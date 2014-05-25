package disis.core.rmi;

import disis.core.IMessageInbox;
import disis.core.IMessageInboxFactory;
import disis.core.exception.DisisException;

import java.rmi.RemoteException;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 24/05/14 15:11
 */
public class RmiInboxFactory implements IMessageInboxFactory {

    @Override
    public IMessageInbox createInbox() {
        try {
            return new RmiMessageInbox();
        } catch (RemoteException exception) {
            throw new DisisException(exception);
        }
    }
}
