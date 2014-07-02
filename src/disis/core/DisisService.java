package disis.core;

import disis.core.configuration.DisisConfiguration;
import disis.core.configuration.LocalConfiguration;
import disis.core.exception.DisisCommunicatorException;
import disis.core.exception.DisisException;
import disis.core.net.ConnectedSimulatorMessage;
import disis.core.net.IMessage;
import disis.core.net.MessageBuilder;
import disis.core.net.ReadyMessage;
import disis.core.net.listeners.ConsoleListener;
import disis.core.net.listeners.MessageListener;
import disis.core.rest.IRestServer;
import disis.core.rest.PushRestServer;
import disis.core.rest.content.RestSimulatorInfo;
import disis.core.utils.ThreadHelper;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14. 4. 2014 22:40
 */
public class DisisService {

    private final DisisCommunicator communicator;
    private final IRestServer restServer;
    private final LocalConfiguration configuration;

    public Map<String, ConnectionInfo> getConnectedSurrounding() {
        return connectedSurrounding;
    }

    private final Map<String, ConnectionInfo> connectedSurrounding = new HashMap<>();
    private IMessageInbox localMessageBox;
    private boolean readyForSimulators = false;

    private static final Object locker = new Object();
    private Map<String, RestSimulatorInfo> localSimulators = new HashMap<>();
    private Map<String, String> remoteSimulators = new HashMap<>();
    private PushRestServer pushRestServer = new PushRestServer();

    public DisisService(DisisCommunicator communicator, IRestServer restServer, LocalConfiguration configuration) {
        this.communicator = communicator;
        this.restServer = restServer;
        this.configuration = configuration;
    }

    public void start() {
        initializeContext();
        startLocalEnvironment(); // RMI
        startPublicEnvironment();  // REST
    }

    private void initializeContext() {
        StaticContext.init(this);
    }

    private void startLocalEnvironment() {
        try {
            startCommunicator();
            connectToSurroundingServices();
        } catch (RemoteException exception) {
            throw new DisisException();
        }
    }

    private void startPublicEnvironment() {
        restServer.start();
    }

    private void sendReadyMessage() throws DisisCommunicatorException {
        IMessage readyMessage = MessageBuilder.createReadyBroadcastMessage(getFullName());
        sendInternalBroadcastMessage(readyMessage);
    }

    public boolean isReadyForSimulators() {
        return readyForSimulators;
    }

    private void sendInternalBroadcastMessage(String message) throws DisisCommunicatorException {
        IMessage internalMessage = MessageBuilder.createInternalBroadcastMessage(getFullName(), message);
        sendInternalBroadcastMessage(internalMessage);
    }

    private void sendInternalBroadcastMessage(IMessage message) throws DisisCommunicatorException {
        for (ConnectionInfo connectionInfo : connectedSurrounding.values()) {
            System.out.println(configuration.getLocalName() + ": sending message to " + connectionInfo.getName() + " (" + message.getMessage() + ")");
            communicator.sendMessage(message, connectionInfo.getInbox());
        }
    }

    private void connectToSurroundingServices() throws RemoteException {
            for (DisisConfiguration disisConfiguration : configuration.getSurroundingServices()) {
                synchronized (locker) {
                    ConnectionInfo connectionInfo = communicator.connect(disisConfiguration);
                    connectedSurrounding.put(connectionInfo.getName(), connectionInfo);
                    System.out.println(configuration.getLocalName() + ": connected to " + connectionInfo.getName());
                }
            }

        sendReadyMessage();
        waitForConnections();
        setReadyForSimulators(true);
    }

    private void waitForConnections() throws RemoteException {
        for (ConnectionInfo connectionInfo : connectedSurrounding.values()) {
            IMessageInbox messageInbox = connectionInfo.getInbox();

            while (!messageInbox.getReceivedMessages().stream()
                    .anyMatch(message -> message instanceof ReadyMessage)) {
                ThreadHelper.sleep(1000);
            }
            System.out.println("Received ReadyMessage");
            connectionInfo.setReady(true);
        }
    }

    private void startCommunicator() throws RemoteException {
        localMessageBox = communicator.start(configuration);
        localMessageBox.addReceivedMessageListener(new MessageListener());

        // just for debugging
        localMessageBox.addReceivedMessageListener(new ConsoleListener(configuration.getLocalName()));
        System.out.println(configuration.getLocalName() + ": local bind successfully completed");
    }

    private void setReadyForSimulators(boolean readyForSimulators) {
        this.readyForSimulators = readyForSimulators;
    }

    private String getFullName() {
        // TODO: need to getting local ip address instead of localhost
        return String.format("localhost:%d/%s", configuration.getLocalPort(), configuration.getLocalName());
    }

    public void connectSimulator(RestSimulatorInfo simulatorInfo) {
        localSimulators.put(simulatorInfo.getRemoteName(), simulatorInfo);
        sendConnectedSimulatorMessage(simulatorInfo.getRemoteName());
        if(isFulfilledDependencies(simulatorInfo)) {
            sendReadyMessageForSimulation(simulatorInfo.getRemoteName());
        }
    }

    private void sendReadyMessageForSimulation(String simulatorName) {
        pushRestServer.sendMessageForSimulation(localSimulators.get(simulatorName));
    }

    private boolean isFulfilledDependencies(RestSimulatorInfo simulatorInfo) {
        // Simulator dependencies
        for(String remoteSimulator : simulatorInfo.getSurroundingSimulators()) {
            if(!localSimulators.containsKey(remoteSimulator) || !remoteSimulators.containsKey(remoteSimulator))
                return false;
        }
        return true;
    }

    public void addRemoteSimulator(String simulatorName, String remoteDisisName) {
        remoteSimulators.put(simulatorName, remoteDisisName);
    }

    private void sendConnectedSimulatorMessage(String simulatorName) {
        try {
            sendInternalBroadcastMessage(new ConnectedSimulatorMessage(getFullName(), simulatorName));
        } catch (DisisCommunicatorException e) {
            e.printStackTrace(); // todo: handle this
        }
    }
}
