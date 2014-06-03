package disis.core.rest;

import com.google.gson.Gson;
import disis.core.DisisService;
import disis.core.StaticContext;
import disis.core.rest.content.RestClientInfo;
import disis.core.rest.content.RestInternalMessage;

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
    }

    @POST
    @Path("send-internal-message")
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendInternalMessage(String internalMessage) {
        RestInternalMessage restInternalMessage = new Gson().fromJson(internalMessage, RestInternalMessage.class);
        // service.sendMessage(restInternalMessage);
    }
}