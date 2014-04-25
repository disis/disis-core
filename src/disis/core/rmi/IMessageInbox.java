package disis.core.rmi;

import disis.core.IMessage;
import disis.core.ReceivedMessageListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 25. 4. 2014 17:07
 */
public interface IMessageInbox extends Remote {
    void sendMessage(IMessage message) throws RemoteException;

    void onReceivedMessage(ReceivedMessageListener receivedMessageListener);
}
