package disis.core.net;

import disis.core.IMessage;

import java.io.Serializable;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14. 4. 2014 22:58
 */
public class InternalMessage implements Serializable, IMessage {

    private String message;

    public InternalMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
