package disis.core.rmi;

import disis.core.IMessageInbox;
import disis.core.IMessageInboxRegistrar;
import disis.core.configuration.DisisConfiguration;
import disis.core.exception.DisisException;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 28.5.14 9:48
 */
public class RmiRegistrar implements IMessageInboxRegistrar {

    @Override
    public void registerMessageInbox(int port, String name, IMessageInbox messageInbox) throws DisisException {
        try {
            Registry registry = getLocalRmiRegistry(port);
            registerLocalRmiObject(registry, name, messageInbox);
        } catch (RemoteException e) {
            throw new DisisException(e);
        }
    }

    @Override
    public IMessageInbox getRemoteInbox(DisisConfiguration configuration) throws DisisException {
        try {
            Registry registry = getRemoteRmiRegistry(configuration.getNetworkAddress(), configuration.getPort());
            return (IMessageInbox) registry.lookup(configuration.getRemoteName());
        } catch (RemoteException | NotBoundException e) {
            throw new DisisException(e);
        }
    }

    private void registerLocalRmiObject(Registry registry, String name, Remote object) throws RemoteException {
        registry.rebind(name, object);
    }

    private Registry getLocalRmiRegistry(int port) throws RemoteException {
        Registry registry = LocateRegistry.getRegistry(port);
        if (isRegistryRunning(registry))
            return registry;
        else
            return LocateRegistry.createRegistry(port);
    }

    private boolean isRegistryRunning(Registry registry) {
        try {
            registry.list();
            return true;
        } catch (RemoteException exception) {
            return false;
        }
    }

    private Registry getRemoteRmiRegistry(String networkAddress, int port) throws RemoteException {
        return LocateRegistry.getRegistry(networkAddress, port);
    }
}
