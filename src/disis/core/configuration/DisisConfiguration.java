package disis.core.configuration;

import com.google.gson.annotations.SerializedName;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14/04/14 20:20
 */
public class DisisConfiguration {

    @SerializedName("title")
    private String title;

    @SerializedName("remote-name")
    private String remoteName;

    @SerializedName("network-address")
    private String networkAddress;

    @SerializedName("port")
    private int port;

    public String getTitle() {
        return title;
    }

    public String getRemoteName() {
        return remoteName;
    }

    public String getNetworkAddress() {
        return networkAddress;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "DisisConfiguration{" +
                "title='" + title + '\'' +
                ", remoteName='" + remoteName + '\'' +
                ", networkAddress='" + networkAddress + '\'' +
                ", port=" + port +
                '}';
    }
}
