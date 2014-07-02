package disis.core.net;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 30. 6. 2014 23:11
 */
public class ConnectedSimulatorMessage extends DisisMessage {

    private final String simulatorName;

    public ConnectedSimulatorMessage(String senderFullName, String simulatorName) {
        super(senderFullName);
        this.simulatorName = simulatorName;
    }

    @Override
    public String getSenderName() {
        return null;
    }

    @Override
    public String getSenderFullName() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    public String getSimulatorName() {
        return simulatorName;
    }
}
