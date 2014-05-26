package disis.core.net.listeners;

import disis.core.net.IMessage;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 24/05/14 11:45
 */
public class ConsoleListener implements ReceivedMessageListener {

    private final String owner;

    public ConsoleListener(String owner) {
        this.owner = owner;
    }

    @Override
    public void messageReceived(IMessage message) {
        System.out.println("\n" +
                        owner + ": received message from " + message.getSenderName()
        );
    }
}
