package disis.core.net;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 25/05/14 00:37
 */
public class ReadyMessage extends DisisMessage {

    public ReadyMessage(String senderFullName) {
        super(senderFullName);
        this.message = "I am ready!";
    }
}
