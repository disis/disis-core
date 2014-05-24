package disis.core;

import disis.core.configuration.DisisConfiguration;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 24/05/14 12:05
 */
public class DisisContainer {

    private final String name;
    private final IMessageInbox inbox;
    private final DisisConfiguration configuration;
    private boolean connected;

    public DisisContainer(DisisConfiguration configuration, IMessageInbox messageInbox, boolean connected) {
        this.name = configuration.getRemoteName();
        this.connected = connected;
        this.inbox = messageInbox;
        this.configuration = configuration;

    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return String.format("%s:%d/%s", configuration.getNetworkAddress(), configuration.getPort(), configuration.getRemoteName());
    }

    public boolean isConnected() {
        return connected;
    }

    public IMessageInbox getInbox() {
        return inbox;
    }

    public DisisConfiguration getConfiguration() {
        return configuration;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}

