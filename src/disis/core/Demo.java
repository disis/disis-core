package disis.core;

import disis.core.configuration.ConfigurationLoader;
import disis.core.configuration.LocalConfiguration;
import disis.core.rest.DisisRestResource;
import disis.core.rest.IRestServer;
import disis.core.rest.RestServer;
import disis.core.rmi.RmiInboxFactory;
import disis.core.rmi.RmiRegistrar;
import disis.core.utils.DisisServiceRunner;
import disis.core.utils.ThreadHelper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14/04/14 19:16
 */
class Demo {

    public static void main(String[] args) throws IOException {
        //demo1();
        //demo2();
        demo3();
    }

    private static void demo1() {
        String configurationPath = new File("src/disis/sample/demo1/configuration-sample.json").getAbsolutePath();
        LocalConfiguration localConfiguration = ConfigurationLoader.load(configurationPath);

        IMessageInboxFactory inboxFactory = new RmiInboxFactory();
        IMessageInboxRegistrar inboxRegistrar = new RmiRegistrar();
        DisisCommunicator communicator = new DisisCommunicator(inboxFactory, inboxRegistrar);
        IRestServer restServer = new RestServer("http://localhost", localConfiguration.getLocalPort(), DisisRestResource.class);
        DisisService service = new DisisService(communicator, restServer, localConfiguration);
        service.start();
    }

    private static void demo2() {
        String configurationPath1 = new File("src/disis/sample/demo2/local-configuration-sample-1.json").getAbsolutePath();
        String configurationPath2 = new File("src/disis/sample/demo2/local-configuration-sample-2.json").getAbsolutePath();
        String configurationPath3 = new File("src/disis/sample/demo2/local-configuration-sample-3.json").getAbsolutePath();

        LocalConfiguration localConfiguration1 = ConfigurationLoader.load(configurationPath1);
        LocalConfiguration localConfiguration2 = ConfigurationLoader.load(configurationPath2);
        LocalConfiguration localConfiguration3 = ConfigurationLoader.load(configurationPath3);

        List<LocalConfiguration> configurations = Arrays.asList(localConfiguration1, localConfiguration2, localConfiguration3);

        for (final LocalConfiguration configuration : configurations) {
            DisisServiceRunner.run(configuration);
            ThreadHelper.sleep(3000);
        }

        SwingUtilities.invokeLater(() -> System.out.println("\nTHIS IS DISIS!!!\n"));

        try {
            System.in.read();
        } catch (IOException ignored) {

        } finally {
            System.exit(0);
        }
    }

    private static void demo3() throws IOException {
        String configurationPath = new File("src/disis/sample/demo3/configuration-sample.json").getAbsolutePath();
        LocalConfiguration localConfiguration = ConfigurationLoader.load(configurationPath);

        IMessageInboxFactory inboxFactory = new RmiInboxFactory();
        IMessageInboxRegistrar inboxRegistrar = new RmiRegistrar();
        DisisCommunicator communicator = new DisisCommunicator(inboxFactory, inboxRegistrar);
        IRestServer restServer = new RestServer("http://localhost", 8099, DisisRestResource.class);
        DisisService service = new DisisService(communicator, restServer, localConfiguration);
        service.start();
    }
}
