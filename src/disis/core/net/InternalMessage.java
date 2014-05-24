package disis.core.net;

import java.io.Serializable;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14. 4. 2014 22:58
 */
public class InternalMessage extends DisisMessage implements Serializable {

    private String message;

    public InternalMessage(String senderFullName, String message) {
        super(senderFullName);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
