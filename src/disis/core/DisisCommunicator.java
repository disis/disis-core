package disis.core;

import disis.core.configuration.DisisConfiguration;
import disis.core.configuration.LocalConfiguration;
import disis.core.exception.DisisCommunicatorException;
import disis.core.net.IMessage;
import disis.core.rmi.RmiMessageInbox;
import disis.core.utils.ThreadHelper;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14/04/14 20:27
 */
public class DisisCommunicator {

    private final int MaxNumberOfRepetitions = 10;
    private final long RepetitionsSleepTime = 1000;

    public IMessageInbox start(LocalConfiguration localConfiguration) throws DisisCommunicatorException {
        try {
            IMessageInbox localMessageInbox = createMessageInbox();
            Registry registry = getLocalRmiRegistry(localConfiguration.getLocalPort());
            registerLocalRmiObject(registry, localConfiguration.getLocalName(), localMessageInbox);

            return localMessageInbox;
        } catch (RemoteException remoteException) {
            throw new DisisCommunicatorException(remoteException);
        }
    }

    private IMessageInbox createMessageInbox() throws RemoteException {
        return new RmiMessageInbox();
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

    public DisisContainer connect(DisisConfiguration disisConfiguration) throws DisisCommunicatorException {
        for (int repetition = 0; repetition < MaxNumberOfRepetitions; repetition++) {
            try {
                Registry registry = getRemoteRmiRegistry(disisConfiguration.getNetworkAddress(), disisConfiguration.getPort());
                IMessageInbox remoteMessageInbox = (IMessageInbox) registry.lookup(disisConfiguration.getRemoteName());
                return new DisisContainer(disisConfiguration, remoteMessageInbox, true);
            } catch (NotBoundException | RemoteException exception) {
                ThreadHelper.sleep(RepetitionsSleepTime);
            }
        }
        throw new DisisCommunicatorException();
    }

    private Registry getRemoteRmiRegistry(String networkAddress, int port) throws RemoteException {
        return LocateRegistry.getRegistry(networkAddress, port);
    }


    public void sendMessage(IMessage message, IMessageInbox messageInbox) throws DisisCommunicatorException {
        try {
            messageInbox.sendMessage(message);
        } catch (RemoteException exception) {
            throw new DisisCommunicatorException(exception);
        }
    }
}
