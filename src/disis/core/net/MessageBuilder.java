package disis.core.net;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 24/05/14 12:01
 */
public final class MessageBuilder {

    public static IMessage createInternalBroadcastMessage(String senderFullName, String message) {
        return new InternalMessage(senderFullName, message);
    }

    public static IMessage createReadyBroadcastMessage(String senderFullName) {
        return new ReadyMessage(senderFullName);
    }

    public static IMessage createNullMessageRequestMessage(String senderFullName, String simulatorName) {
        return new NullMessageRequestMessage(senderFullName, simulatorName);
    }
}
