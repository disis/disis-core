package disis.core.rest;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import disis.core.DisisService;
import disis.core.StaticContext;
import disis.core.rest.content.RestClientInfo;
import disis.core.rest.content.RestMessage;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 2.6.14 13:20
 */

@Path("disis")
public class DisisRestResource {

    private final DisisService service;

    public DisisRestResource() {
        service = StaticContext.get();
    }

    @POST
    @Path("connect")
    @Consumes(MediaType.APPLICATION_JSON)
    public void connect(String rawClientInfo) {
        RestClientInfo clientInfo = new Gson().fromJson(rawClientInfo, RestClientInfo.class);
        service.getLocalClients().put(clientInfo.getRemoteName(), clientInfo);

        Client client = Client.create();
        WebResource resource = client.resource(clientInfo.getEndPointAddress()).path("connected");
        resource.post();
    }

    @POST
    @Path("send-internal-message")
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendMessage(String rawMessage) {
        RestMessage restMessage = new Gson().fromJson(rawMessage, RestMessage.class);
        // service.sendMessage(restMessage);
    }

    @POST
    @Path("test")
    public String test() {
        return "hello";
    }
}