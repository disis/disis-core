package disis.core;

import disis.core.exception.DisisException;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 03/06/14 23:57
 */
public class StaticContext {

    private static DisisService service;

    public static void init(DisisService service) {
        if (StaticContext.service != null)
            throw new DisisException();

        StaticContext.service = service;
    }

    public static DisisService get() {
        return service;
    }

}
