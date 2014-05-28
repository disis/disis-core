package disis.core.utils;

import disis.core.DisisCommunicator;
import disis.core.DisisService;
import disis.core.IMessageInboxFactory;
import disis.core.IMessageInboxRegistrar;
import disis.core.configuration.LocalConfiguration;
import disis.core.rmi.RmiInboxFactory;
import disis.core.rmi.RmiRegistrar;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 26.5.14 14:54
 */

public class DisisServiceRunner {
    private DisisServiceRunner() {
    }

    public static void run(LocalConfiguration configuration) {
        Thread disisThread = new Thread(() -> {
            IMessageInboxFactory inboxFactory = new RmiInboxFactory();
            IMessageInboxRegistrar inboxRegistrar = new RmiRegistrar();
            DisisCommunicator communicator = new DisisCommunicator(inboxFactory, inboxRegistrar);

            DisisService service = new DisisService(communicator, configuration);
            service.start();

            System.out.println(String.format("%s {localhost:%d} started", configuration.getLocalName(), configuration.getLocalPort()));

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    LocateRegistry.getRegistry(configuration.getLocalPort()).unbind(configuration.getLocalName());
                    System.out.println(String.format("%s: shutting down", configuration.getLocalName()));
                } catch (RemoteException | NotBoundException e) {
                    e.printStackTrace();
                }
            }));
        });
        disisThread.start();
    }
}
