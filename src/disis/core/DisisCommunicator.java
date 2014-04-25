package disis.core;

import disis.core.configuration.DisisConfiguration;
import disis.core.configuration.LocalConfiguration;
import disis.core.rmi.IMessageInbox;
import disis.core.rmi.RmiMessageInbox;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14/04/14 20:27
 */
public class DisisCommunicator {

    private IMessageInbox localMessageInbox;
    private Map<String, IMessageInbox> remoteMessageInboxes = new HashMap<>();

    public void start(LocalConfiguration localConfiguration) {
        try {
            localMessageInbox = new RmiMessageInbox();
            Naming.rebind(localConfiguration.getLocalName(), localMessageInbox);
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public boolean connect(DisisConfiguration disisConfiguration) {
        try {
            IMessageInbox remoteMessageInbox = (IMessageInbox) Naming.lookup(disisConfiguration.getRemoteName());
            remoteMessageInboxes.put(disisConfiguration.getRemoteName(), remoteMessageInbox);
            return true;
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            return false;
        }
    }

    public void sendInternalBroadcastMessage(InternalMessage message) {
        for(IMessageInbox messageInbox : remoteMessageInboxes.values()) {
            try {
                messageInbox.sendMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
