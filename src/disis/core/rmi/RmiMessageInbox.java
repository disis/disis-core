package disis.core.rmi;

import disis.core.IMessage;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 25. 4. 2014 17:07
 */
public class RmiMessageInbox extends UnicastRemoteObject implements IMessageInbox {

    public RmiMessageInbox() throws RemoteException {
    }

    @Override
    public void sendMessage(IMessage message) throws RemoteException {

    }
}
