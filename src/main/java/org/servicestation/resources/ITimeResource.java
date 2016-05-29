package org.servicestation.resources;

import org.servicestation.resources.dto.BusyTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/busytime")
public interface ITimeResource {

    @Path("/{stationId}/{timestamp}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    BusyTime getBusyTime(@PathParam("stationId") Integer stationId, @PathParam("timestamp") String timestamp);

}
