package disis.core;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14/04/14 20:12
 */
public class LocalConfiguration {

    private DisisConfiguration[] neighbourhood;

    public static LocalConfiguration load() {
        return null;
    }

    public DisisConfiguration[] getSurroundingServices() {
        return neighbourhood;
    }
}
