package disis.core.net.listeners;

import disis.core.net.IMessage;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 25. 4. 2014 17:29
 */
public interface ReceivedMessageListener {

    void messageReceived(IMessage message);
}
