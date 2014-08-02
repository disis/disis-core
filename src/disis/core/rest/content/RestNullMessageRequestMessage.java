package disis.core.rest.content;

import com.google.gson.annotations.SerializedName;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 2. 8. 2014 15:13
 */
public class RestNullMessageRequestMessage {
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

    @SerializedName("current-lvt")
    private double currentLVT;

    public RestNullMessageRequestMessage() {
    }

    public RestNullMessageRequestMessage(String from, String to, double currentLVT) {
        this.from = from;
        this.to = to;
        this.message = "null";
        this.currentLVT = currentLVT;
    }

    public double getCurrentLVT() {
    
        return currentLVT;
    }
}
