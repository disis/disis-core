package disis.core.rest;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import disis.core.DisisRestResource;
import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;
import java.net.URI;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 2.6.14 14:05
 */
public class GrizzlyServer {
    private HttpServer server;
    private Class resource;

    public GrizzlyServer(Class resource) {
        this.resource = resource;
    }

    public void start(int port) {
        URI BASE_URI = URI.create(String.format("http://localhost:%d", port));
        ResourceConfig resourceConfig = new ClassNamesResourceConfig(resource);
        try {
            server = GrizzlyServerFactory.createHttpServer(BASE_URI, resourceConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        server.stop();
    }
}
