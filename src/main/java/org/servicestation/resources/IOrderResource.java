package org.servicestation.resources;

import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.OrderDto;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/order")
public interface IOrderResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    FullOrderDto createNewOrder(@Context SecurityContext securityContext, FullOrderDto orderDto);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{stationId}/{timestamp}")
    List<FullOrderDto> getAllOrdersByStationIdAndDate(@PathParam("stationId") Integer stationId, @PathParam("timestamp") String timestamp);
}
