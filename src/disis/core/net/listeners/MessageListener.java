package disis.core.net.listeners;

import disis.core.StaticContext;
import disis.core.net.ConnectedSimulatorMessage;
import disis.core.net.IMessage;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 2. 7. 2014 12:50
 */
public class MessageListener implements ReceivedMessageListener {
    @Override
    public void messageReceived(IMessage message) {
        if(message instanceof ConnectedSimulatorMessage) {
            String simulatorName = ((ConnectedSimulatorMessage)message).getSimulatorName();
            String remoteDisisName = message.getSenderFullName();
            StaticContext.get().addRemoteSimulator(simulatorName, remoteDisisName);
        }
    }
}
