package disis.core;

import disis.core.configuration.DisisConfiguration;
import disis.core.configuration.LocalConfiguration;

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

    private Map<String, Boolean> connectedSurrounding = new HashMap<>();
    private boolean ready = false;

    public DisisService(DisisCommunicator communicator, LocalConfiguration configuration) {
        this.communicator = communicator;
        this.configuration = configuration;
    }

    public void start() {
        startCommunicator();
        connectToSurroundingServices();
        sendReadyMessage();
    }

    public boolean isReady() {
        return ready;
    }

    private void sendReadyMessage() {
        InternalMessage message = new InternalMessage("ready");
        communicator.sendInternalBroadcastMessage(message);
    }

    private void connectToSurroundingServices() {
        for (DisisConfiguration disisConfiguration : configuration.getSurroundingServices()) {
            boolean connected = communicator.connect(disisConfiguration);
            connectedSurrounding.put(disisConfiguration.getRemoteName(), connected);
        }

        if (!areAllServicesConnected()) {
            waitForConnections();
        }

        setReady(true);
    }

    private void waitForConnections() {

    }

    private void startCommunicator() {
        communicator.start(configuration);
    }

    private boolean areAllServicesConnected() {
        for (boolean connected : connectedSurrounding.values()) {
            if (!connected) return false;
        }
        return true;
    }

    private void setReady(boolean ready) {
        this.ready = ready;
    }
}
