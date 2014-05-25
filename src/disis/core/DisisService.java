package disis.core;

import disis.core.configuration.DisisConfiguration;
import disis.core.configuration.LocalConfiguration;
import disis.core.exception.DisisCommunicatorException;
import disis.core.exception.DisisException;
import disis.core.net.IMessage;
import disis.core.net.MessageBuilder;
import disis.core.net.ReadyMessage;
import disis.core.net.listeners.ConsoleListener;
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

    private DisisCommunicator communicator;
    private LocalConfiguration configuration;
    private IMessageInbox localMessageBox;
    private Map<String, ConnectionInfo> connectedSurrounding = new HashMap<>();
    private boolean ready = false;

    private static final Object locker = new Object();

    public DisisService(DisisCommunicator communicator, LocalConfiguration configuration) {
        this.communicator = communicator;
        this.configuration = configuration;
    }

    public void start() {
        startLocalEnvironment(); // RMI
        startPublicEnvironment();  // REST
    }

    public void startLocalEnvironment() {
        try {
            startCommunicator();
            connectToSurroundingServices();
        } catch (RemoteException exception) {
            throw new DisisException();
        }
    }

    public void startPublicEnvironment() {
        // TODO start REST API
    }

    private void sendReadyMessage() throws DisisCommunicatorException {
        IMessage readyMessage = MessageBuilder.createReadyBroadcastMessage(getFullName());
        sendInternalBroadcastMessage(readyMessage);
    }

    public boolean isReady() {
        return ready;
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
        setReady(true);
    }

    private void waitForConnections() throws RemoteException {
        for (ConnectionInfo connectionInfo : connectedSurrounding.values()) {
            IMessageInbox messageInbox = connectionInfo.getInbox();

            while (!messageInbox.getReceivedMessages().stream()
                    .anyMatch(message -> message instanceof ReadyMessage)) {
                System.out.println("Waiting for ReadyMessage");
                ThreadHelper.sleep(1000);
            }

        }
    }

    private void startCommunicator() throws RemoteException {
        localMessageBox = communicator.start(configuration);

        // just for debugging
        localMessageBox.addReceivedMessageListener(new ConsoleListener(configuration.getLocalName()));
        System.out.println(configuration.getLocalName() + ": local bind successfully completed");
    }

    private boolean areAllServicesConnected() {
        return connectedSurrounding.values().stream().allMatch(ConnectionInfo::isConnected);
    }

    private void setReady(boolean ready) {
        this.ready = ready;
    }

    private String getFullName() {
        // TODO: need to getting local ip address instead of localhost
        return String.format("localhost:%d/%s", configuration.getLocalPort(), configuration.getLocalName());
    }
}
