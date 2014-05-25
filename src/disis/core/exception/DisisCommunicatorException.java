package disis.core.exception;


import java.rmi.RemoteException;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 24/05/14 10:34
 */
public class DisisCommunicatorException extends RemoteException {

    public DisisCommunicatorException() {
        super("WTF!!!");
    }

    public DisisCommunicatorException(Exception exception) {
        super(exception.getMessage());
    }

    public DisisCommunicatorException(RemoteException exception) {
        super(exception.getMessage());
    }

    public DisisCommunicatorException(String message) {
        super(message);
    }
}
