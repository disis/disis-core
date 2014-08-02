package disis.core.rest.content;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 04/06/14 00:21
 */
public class RestSimulatorInfo {

    // Fast Food Left
    @SerializedName("title")
    public String title;

    // fast-food-II
    @SerializedName("remote-name")
    public String remoteName;

    // Implemented in Java platform
    @SerializedName("description")
    public String description;

    // http://localhost:1000/fast-food-ii/push/
    @SerializedName("end-point-address")
    public String endPointAddress;

    // ["fast-food-I", "highway-I"]
    @SerializedName("surrounding-simulators")
    public List<String> surroundingSimulators;

    public String getTitle() {
        return title;
    }

    public String getRemoteName() {
        return remoteName;
    }

    public String getDescription() {
        return description;
    }

    public String getEndPointAddress() {
        return endPointAddress;
    }

    public List<String> getSurroundingSimulators() {
        return surroundingSimulators;
    }
}
