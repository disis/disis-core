package disis.core.exception;


/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 24/05/14 10:34
 */
public class DisisCommunicatorException extends Exception {

    public DisisCommunicatorException(Exception exception) {
        super(exception);
    }
}
