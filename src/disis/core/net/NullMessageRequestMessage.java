package disis.core.net;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 6. 7. 2014 15:38
 */
public class NullMessageRequestMessage extends DisisMessage {
    private final String simulatorName;

    public NullMessageRequestMessage(String senderFullName, String simulatorName) {
        super(senderFullName);
        this.simulatorName = simulatorName;
    }

    public String getSimulatorName() {
        return simulatorName;
    }
}
