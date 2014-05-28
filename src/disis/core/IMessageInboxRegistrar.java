package disis.core;

import disis.core.configuration.DisisConfiguration;
import disis.core.exception.DisisException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 28.5.14 10:21
 */
public interface IMessageInboxRegistrar {
    void registerMessageInbox(int port, String name, IMessageInbox messageInbox) throws DisisException;

    IMessageInbox getRemoteInbox(DisisConfiguration configuration) throws DisisException;
}
