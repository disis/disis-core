package disis.core.utils;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 25/05/14 00:14
 */
public final class ThreadHelper {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            sleep(millis);
        }
    }
}
