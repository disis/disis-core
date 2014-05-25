package disis.core.rmi;

import disis.core.IMessageInbox;
import disis.core.net.IMessage;
import disis.core.net.listeners.ReceivedMessageListener;

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

    private final List<ReceivedMessageListener> messageListeners;
    private final List<IMessage> receivedMessages;

    public RmiMessageInbox() throws RemoteException {
        super();
        messageListeners = new ArrayList<>();
        receivedMessages = new ArrayList<>();
    }

    @Override
    public void sendMessage(IMessage message) throws RemoteException {
        receivedMessages.add(message);

        for (ReceivedMessageListener listener : messageListeners) {
            listener.messageReceived(message);
        }
    }

    @Override
    public List<IMessage> getReceivedMessages() throws RemoteException {
        return receivedMessages;
    }

    @Override
    public void addReceivedMessageListener(ReceivedMessageListener receivedMessageListener) throws RemoteException {
        messageListeners.add(receivedMessageListener);
    }
}
