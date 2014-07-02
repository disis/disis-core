package disis.core;

import disis.core.configuration.DisisConfiguration;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 24/05/14 12:05
 */
public class ConnectionInfo {

    private final String name;
    private final IMessageInbox inbox;
    private final DisisConfiguration configuration;
    private boolean ready;

    public ConnectionInfo(DisisConfiguration configuration, IMessageInbox messageInbox) {
        this.name = configuration.getRemoteName();
        this.inbox = messageInbox;
        this.configuration = configuration;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return String.format("%s:%d/%s", configuration.getNetworkAddress(), configuration.getPort(), configuration.getRemoteName());
    }

    public boolean isReady() {
        return ready;
    }

    public IMessageInbox getInbox() {
        return inbox;
    }

    public DisisConfiguration getConfiguration() {
        return configuration;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}

