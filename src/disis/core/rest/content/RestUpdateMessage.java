package disis.core.rest.content;

import com.google.gson.annotations.SerializedName;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 2. 7. 2014 16:23
 */
public class RestUpdateMessage {
    @SerializedName("client-info")
    private final RestSimulatorInfo clientInfo;

    @SerializedName("local-virtual-time")
    private final double localVirtualTime;

    public RestUpdateMessage(RestSimulatorInfo clientInfo, double localVirtualTime) {

        this.clientInfo = clientInfo;
        this.localVirtualTime = localVirtualTime;
    }

    public RestSimulatorInfo getClientInfo() {
        return clientInfo;
    }

    public double getLocalVirtualTime() {
        return localVirtualTime;
    }
}
