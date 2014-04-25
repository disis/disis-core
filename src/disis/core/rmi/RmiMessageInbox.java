package disis.core.rmi;

import disis.core.IMessage;
import disis.core.ReceivedMessageListener;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 25. 4. 2014 17:07
 */
public class RmiMessageInbox extends UnicastRemoteObject implements IMessageInbox {

    private List<ReceivedMessageListener> messageListeners = new ArrayList<>();

    public RmiMessageInbox() throws RemoteException {
    }

    @Override
    public void sendMessage(IMessage message) throws RemoteException {
        for (ReceivedMessageListener listener : messageListeners) {
            listener.messageReceived(message);
        }
    }

    @Override
    public void onReceivedMessage(ReceivedMessageListener receivedMessageListener) {
        messageListeners.add(receivedMessageListener);
    }
}
