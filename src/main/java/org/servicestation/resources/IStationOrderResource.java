package org.servicestation.resources;

import org.servicestation.resources.dto.StationOrderDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("stationorder")
public interface IStationOrderResource {

    @GET
    @Path("/{stationId}/{timestamp}")
    @Produces(MediaType.APPLICATION_JSON)
    List<StationOrderDto> getAllStationOrders(@PathParam("stationId") Integer stationId, @PathParam("timestamp") String timestamp);
}
