package disis.core;

import disis.core.configuration.DisisConfiguration;
import disis.core.configuration.LocalConfiguration;
import disis.core.rmi.IMessageInbox;
import disis.core.rmi.RmiMessageInbox;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
            localMessageInbox.onReceivedMessage(message -> {
                // do something
            });
            getLocalRmiRegistry(localConfiguration.getLocalPort()).rebind(localConfiguration.getLocalName(), localMessageInbox);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private Registry getLocalRmiRegistry(int port) throws RemoteException {

        Registry registry = LocateRegistry.getRegistry(port);
        if (isRegistryRunning(registry))
            return registry;
        else
            return LocateRegistry.createRegistry(port);
    }

    private boolean isRegistryRunning(Registry registry) throws RemoteException {
        try {
            registry.list();
            return true;
        } catch (ConnectException e){
            return false;
        }
    }

    public boolean connect(DisisConfiguration disisConfiguration) {
        try {
            Registry registry = getRemoteRmiRegistry(disisConfiguration.getNetworkAddress(), disisConfiguration.getPort());
            IMessageInbox remoteMessageInbox = (IMessageInbox) registry.lookup(disisConfiguration.getRemoteName());
            remoteMessageInboxes.put(disisConfiguration.getRemoteName(), remoteMessageInbox);
            return true;
        } catch (NotBoundException | RemoteException e) {
            return false;
        }
    }

    private Registry getRemoteRmiRegistry(String networkAddress, int port) throws RemoteException {
        return LocateRegistry.getRegistry(networkAddress, port);
    }

    public void sendInternalBroadcastMessage(InternalMessage message) {
        for (IMessageInbox messageInbox : remoteMessageInboxes.values()) {
            try {
                messageInbox.sendMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
