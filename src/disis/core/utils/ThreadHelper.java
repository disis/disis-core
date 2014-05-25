package disis.core.utils;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 25/05/14 00:14
 */
public final class ThreadHelper {

    public static void sleep(long millis) {
        long sleepStartedAt = System.currentTimeMillis();
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            long sleepInterruptedAt = System.currentTimeMillis();
            long remainingSleepTime = millis - (sleepInterruptedAt - sleepStartedAt);
            sleep(remainingSleepTime);
        }
    }
}
