package disis.core;

import java.util.HashMap;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14/04/14 19:16
 */
public class Demo {

    private static HashMap<DisisConfiguration, Boolean> connectedSurrounding;
    private static boolean ready;

    public static void main(String[] args) {
        LocalConfiguration localConfiguration = LocalConfiguration.load();
        DisisCommunicator communicator = new DisisCommunicator();

        communicator.start(localConfiguration);
        for (DisisConfiguration disisConfiguration : localConfiguration.getSurroundingServices()) {
            boolean connected = communicator.connect(disisConfiguration);
            connectedSurrounding.put(disisConfiguration, connected);
        }

        if (!isReady()) {
            waitForConnections();
        }

        setReady(true);
        communicator.distributeBroadcastInternalMessage();
    }

    private static void waitForConnections() {

    }

    public static boolean isReady() {
        for (Boolean connected : connectedSurrounding.values()) {
            if (!connected) return false;
        }
        return true;
    }

    public static void setReady(boolean ready) {
        Demo.ready = ready;
    }
}
