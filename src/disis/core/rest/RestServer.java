package disis.core.rest;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import disis.core.exception.DisisException;
import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;
import java.net.URI;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 04/06/14 00:10
 */
public class RestServer implements IRestServer {

    private HttpServer server;
    private Class resource;
    private URI baseUri;

    public RestServer(String uri, int port, Class resource) {
        this.baseUri = URI.create(String.format("%s:%d", uri, port));
        this.resource = resource;
    }

    @Override
    public void start() {
        ResourceConfig resourceConfig = new ClassNamesResourceConfig(resource);
        try {
            server = GrizzlyServerFactory.createHttpServer(baseUri, resourceConfig);

            if (!server.isStarted())
                throw new DisisException();

        } catch (IOException exception) {
            throw new DisisException(exception);
        }
    }

    @Override
    public void stop() {
        if (server.isStarted())
            server.stop();
    }
}
