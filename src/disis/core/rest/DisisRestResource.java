package disis.core.rest;

import com.google.gson.Gson;
import disis.core.DisisService;
import disis.core.StaticContext;
import disis.core.rest.content.RestMessage;
import disis.core.rest.content.RestSimulatorInfo;
import disis.core.rest.content.RestUpdateMessage;

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
        System.out.println("connect");
        RestSimulatorInfo clientInfo = new Gson().fromJson(rawClientInfo, RestSimulatorInfo.class);
        service.connectSimulator(clientInfo);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("update-simulation-timestamp")
    public void updateSimulationTimestamp(String rawUpdateMessage) {
        RestUpdateMessage updateMessage = new Gson().fromJson(rawUpdateMessage, RestUpdateMessage.class);
        service.updateLVT(updateMessage.getClientInfo(), updateMessage.getLocalVirtualTime());
    }

    @POST
    @Path("send-message")
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendMessage(String rawMessage) {
        RestMessage restMessage = new Gson().fromJson(rawMessage, RestMessage.class);
        // service.sendMessage(restMessage);
    }
}