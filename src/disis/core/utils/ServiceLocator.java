package disis.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 03/06/14 23:59
 */
public class ServiceLocator<Identifier> {

    private final Map<Identifier, Object> objectMap;

    public ServiceLocator() {
        objectMap = new HashMap<>();
    }

    public <TClass> TClass bind(Identifier identifier, TClass object) {
        objectMap.put(identifier, object);
        return object;
    }

    public <TClass> TClass get(Identifier identifier) {
        return (TClass) objectMap.get(identifier);
    }
}
