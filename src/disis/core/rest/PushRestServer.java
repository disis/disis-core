package disis.core.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import disis.core.rest.content.RestSimulatorInfo;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 2. 7. 2014 13:31
 */
public class PushRestServer {
    public void sendMessageForSimulation(RestSimulatorInfo simulatorInfo) {
        WebResource resource = createResource(simulatorInfo, "start-simulation");
        resource.post();
    }

    private WebResource createResource(RestSimulatorInfo simulatorInfo, String method) {
        Client client = Client.create();
        return client.resource(simulatorInfo.getEndPointAddress()).path(method);
    }
}
