package disis.core;

import disis.core.configuration.DisisConfiguration;
import disis.core.configuration.LocalConfiguration;
import disis.core.exception.DisisCommunicatorException;
import disis.core.exception.DisisException;
import disis.core.net.IMessage;
import disis.core.utils.ThreadHelper;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14/04/14 20:27
 */
public class DisisCommunicator {

    private final int MAX_NUMBER_OF_RETRIES = 10;
    private final long REPEAT_SLEEP_TIME = 1000;
    private final IMessageInboxFactory inboxFactory;
    private IMessageInboxRegistrar inboxRegistrar;

    public DisisCommunicator(IMessageInboxFactory inboxFactory, IMessageInboxRegistrar inboxRegistrar) {
        this.inboxFactory = inboxFactory;
        this.inboxRegistrar = inboxRegistrar;
    }

    public IMessageInbox start(LocalConfiguration localConfiguration) throws DisisCommunicatorException {
        try {
            IMessageInbox localMessageInbox = inboxFactory.createInbox();
            inboxRegistrar.registerMessageInbox(localConfiguration.getLocalPort(), localConfiguration.getLocalName(), localMessageInbox);

            return localMessageInbox;
        } catch (DisisException exception) {
            throw new DisisCommunicatorException(exception);
        }
    }

    public ConnectionInfo connect(DisisConfiguration disisConfiguration) throws DisisCommunicatorException {
        for (int attemptNumber = 0; attemptNumber < MAX_NUMBER_OF_RETRIES; attemptNumber++) {
            try {
                IMessageInbox remoteMessageInbox = inboxRegistrar.getRemoteInbox(disisConfiguration);
                return new ConnectionInfo(disisConfiguration, remoteMessageInbox, true);
            } catch (DisisException exception) {
                ThreadHelper.sleep(REPEAT_SLEEP_TIME);
            }

        }

        throw new DisisCommunicatorException("Destination unreachable: " + disisConfiguration.getRemoteName());
    }

    public void sendMessage(IMessage message, IMessageInbox messageInbox) throws DisisCommunicatorException {
        try {
            messageInbox.sendMessage(message);
        } catch (RemoteException exception) {
            throw new DisisCommunicatorException(exception);
        }
    }
}
