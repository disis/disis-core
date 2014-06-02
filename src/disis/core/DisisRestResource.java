package disis.core;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * This is DISIS
 * Authors: Jirka Penzes & Jan Voracek
 * Date: 2.6.14 13:20
 */

@Path("disis")
public class DisisRestResource {

    @POST
    @Path("connect")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postConnect(String rawClientInfo){
        Gson gson = new Gson();
        ClientInfo clientInfo = gson.fromJson(rawClientInfo, ClientInfo.class);
        // What now? Service locator powa!
    }
}

class ClientInfo {
    public String address;
    public int port;
}