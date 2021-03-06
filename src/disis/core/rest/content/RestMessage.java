package disis.core.rest.content;

import com.google.gson.annotations.SerializedName;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 04/06/14 00:32
 */
public class RestMessage {

    // fast-food-ii
    @SerializedName("from")
    public String from;

    // highway
    @SerializedName("to")
    public String to;

    // Hello!
    @SerializedName("message")
    public String message;

    public RestMessage() {
    }

    public RestMessage(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }
}
