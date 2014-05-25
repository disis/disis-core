package disis.core;

import disis.core.net.IMessage;
import disis.core.net.listeners.ReceivedMessageListener;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 25. 4. 2014 17:07
 */
public interface IMessageInbox extends Remote {

    void sendMessage(IMessage message) throws RemoteException;

    List<IMessage> getReceivedMessages() throws RemoteException;

    void addReceivedMessageListener(ReceivedMessageListener receivedMessageListener) throws RemoteException;
}
