package disis.core.configuration;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14/04/14 20:12
 */
public class LocalConfiguration {

    @SerializedName("local-name")
    private final String localName;

    @SerializedName("local-port")
    private final int localPort;

    @SerializedName("surrounding-services")
    private final List<DisisConfiguration> surroundingServices;

    public LocalConfiguration() {
        // default configuration
        this("unknown-disis", 1099);
    }

    public LocalConfiguration(String localName, int localPort) {
        this.localName = localName;
        this.localPort = localPort;
        this.surroundingServices = new ArrayList<>();
    }

    public String getLocalName() {
        return localName;
    }

    public int getLocalPort() {
        return localPort;
    }

    public List<DisisConfiguration> getSurroundingServices() {
        return surroundingServices;
    }

    @Override
    public String toString() {
        return "LocalConfiguration{" +
                "localName='" + getLocalName() + '\'' +
                ", localPort=" + getLocalPort() +
                ", surroundingServices=" + getSurroundingServices() +
                '}';
    }
}
