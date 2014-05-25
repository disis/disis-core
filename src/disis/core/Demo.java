package disis.core;

import disis.core.configuration.ConfigurationLoader;
import disis.core.configuration.LocalConfiguration;
import disis.core.rmi.RmiInboxFactory;
import disis.core.utils.ThreadHelper;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 14/04/14 19:16
 */
public class Demo {

    public static void main(String[] args) {
        // demo1();
        demo2();
    }


    private static void demo1() {
        String configurationPath = new File("src/disis/sample/demo1/local-configuration-sample.json").getAbsolutePath();
        LocalConfiguration localConfiguration = ConfigurationLoader.load(configurationPath);

        IMessageInboxFactory inboxFactory = new RmiInboxFactory();
        DisisCommunicator communicator = new DisisCommunicator(inboxFactory);
        DisisService service = new DisisService(communicator, localConfiguration);

        service.start();
    }

    private static void demo2() {
        String configurationPath1 = new File("src/disis/sample/demo2/local-configuration-sample-1.json").getAbsolutePath();
        String configurationPath2 = new File("src/disis/sample/demo2/local-configuration-sample-2.json").getAbsolutePath();

        LocalConfiguration localConfiguration1 = ConfigurationLoader.load(configurationPath1);
        LocalConfiguration localConfiguration2 = ConfigurationLoader.load(configurationPath2);

        List<LocalConfiguration> configurations = Arrays.asList(localConfiguration1, localConfiguration2);

        for (final LocalConfiguration configuration : configurations) {
            Thread disisThread = new Thread(() -> {
                IMessageInboxFactory inboxFactory = new RmiInboxFactory();
                DisisCommunicator communicator = new DisisCommunicator(inboxFactory);

                DisisService service = new DisisService(communicator, configuration);
                service.start();

                System.out.println(String.format("%s {localhost:%d} started", configuration.getLocalName(), configuration.getLocalPort()));
            });
            disisThread.start();
            ThreadHelper.sleep(3000);
        }

        SwingUtilities.invokeLater(() -> {
            System.out.println("\nTHIS IS DISIS!!!\n");
        });
    }
}
