package disis.core;

import disis.core.configuration.DisisConfiguration;
import disis.core.configuration.LocalConfiguration;
import disis.core.exception.DisisCommunicatorException;
import disis.core.exception.DisisException;
import disis.core.net.IMessage;
import disis.core.net.MessageBuilder;
import disis.core.net.ReadyMessage;
import disis.core.net.listeners.ConsoleListener;
import disis.core.rest.IRestServer;
import disis.core.rest.content.RestClientInfo;
import disis.core.rest.content.RestMessage;
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
    private final Map<String, ConnectionInfo> connectedSurrounding = new HashMap<>();
    private IMessageInbox localMessageBox;
    private boolean ready = false;

    private static final Object locker = new Object();
    private HashMap<String, RestClientInfo> localClients = new HashMap<>();

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

    private void setReady(boolean ready) {
        this.ready = ready;
    }

    private String getFullName() {
        // TODO: need to getting local ip address instead of localhost
        return String.format("localhost:%d/%s", configuration.getLocalPort(), configuration.getLocalName());
    }

    public HashMap<String, RestClientInfo> getLocalClients() {
        return localClients;
    }

    public void sendSimulatorMessage(RestMessage restMessage) throws DisisCommunicatorException {
        // Potrebujeme predat zpravu z jednoho simulatoru na druhy - simulator poslal pozadavek:
        // fast-food-ii -> highway
        // interne si zatim ukladame pouze disis jmena, je potreba zjistit ke ktere disis simulator patri
        //
        // napadaji me ted dve reseni:
        //


        /*
        // prvni pres RMI objekt

        IMessageInbox toInbox = null;
        for (ConnectionInfo connectionInfo : connectedSurrounding.values()) {
            IMessageInbox messageInbox = connectionInfo.getInbox();

            List<Client> registeredClients = messageInbox.getRegisteredClients();
            if (registeredClients.contains(restInternalMessage.getTo())) {
                toInbox = messageInbox;
            }
        }

        // nejedna se o sousedni disis -> poslu broadcast na vsechny a hledam, kde je simulator pripojen
        if (toInbox == null) {
            IMessage message = new InternalMessage(getFullName(), "search-client:" + restInternalMessage.getTo());
            sendInternalBroadcastMessage(message);

            registerMessageToListener(restInternalMessage);
            // -> je potreba pridat pro message box nejaky listener, ktery bude cekat na odpoved od disis, ktera ze simulatorem komunikuje -> a odesle zpravu
            // a pripadne klienta ulozi nekam do cache (cache by se prochaze pred sousednima disis)
        }


        // 2 cesta
        // neukladam seznam klientu v RMI, tj. hned odeslu broadcast message
        IMessage message = new InternalMessage(getFullName(), "search-client:" + restInternalMessage.getTo());
        sendInternalBroadcastMessage(message);
        registerMessageToListener(restInternalMessage);

        // zase by bylo vhodne ukladat odpovedi nekam do cache, aby se nemusel porad posilat broadcast

        */
    }
}
