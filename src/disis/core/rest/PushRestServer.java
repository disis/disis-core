package disis.core.rest;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import disis.core.rest.content.RestNullMessageRequestMessage;
import disis.core.rest.content.RestSimulatorInfo;

import javax.ws.rs.core.MediaType;

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

    public void sendNullMessageRequest(RestSimulatorInfo simulatorInfo, RestNullMessageRequestMessage message) {
        WebResource resource = createResource(simulatorInfo, "null-message-request");
        Gson gson = new Gson();
        resource.type(MediaType.APPLICATION_JSON).post(gson.toJson(message));
    }
}
