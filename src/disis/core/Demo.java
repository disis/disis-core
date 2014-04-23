package disis.core;

import disis.core.configuration.ConfigurationLoader;
import disis.core.configuration.LocalConfiguration;

import java.io.File;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14/04/14 19:16
 */
public class Demo {

    public static void main(String[] args) {
        String configurationPath = new File("src/disis/sample/demo1/configuration-sample.disis").getAbsolutePath();
        LocalConfiguration localConfiguration = ConfigurationLoader.load(configurationPath);

        DisisCommunicator communicator = new DisisCommunicator();
        DisisService service = new DisisService(communicator, localConfiguration);

        service.start();
    }
}
