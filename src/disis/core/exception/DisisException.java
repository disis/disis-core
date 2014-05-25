package disis.core.exception;

import java.rmi.RemoteException;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 24/05/14 15:11
 */
public class DisisException extends RuntimeException {

    public DisisException() {
        super("Argh!!! - You don't want this!");
    }

    public DisisException(RemoteException exception) {
        super(exception);
    }
}
