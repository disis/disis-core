package disis.core.rest.content;

import com.google.gson.annotations.SerializedName;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 04/06/14 00:32
 */
public class RestInternalMessage {

    // Non-simulation message (for diagnostic, statistics, ...)

    // fast-food-ii
    @SerializedName("from")
    public String from;

    // highway
    @SerializedName("to")
    public String to;

    // Hello!
    @SerializedName("message")
    public String message;

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
