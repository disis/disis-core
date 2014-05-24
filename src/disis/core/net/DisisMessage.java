package disis.core.net;

import java.io.Serializable;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 24/05/14 11:46
 */
public class DisisMessage implements Serializable, IMessage {

    // please see sample in comments

    // disis1
    private String senderName;

    // 192.168.1.1:1099/disis1
    private String senderFullName;

    // disis2
    //private String recipientName;

    // 192.168.1.2:1089/disis2
    //private String recipientFullName;

    protected String message;

    public DisisMessage(String senderFullName) {
        this.senderFullName = senderFullName;
        //this.recipientFullName = recipientFullName;

        this.senderName = senderFullName.split("/")[1];
        //this.recipientName = recipientFullName.split("/")[1];
    }

    @Override
    public String getSenderName() {
        return senderName;
    }

    @Override
    public String getSenderFullName() {
        return senderFullName;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "DisisMessage{" +
                "senderName='" + senderName + '\'' +
                ", senderFullName='" + senderFullName + '\'' +
                '}';
    }
}
