package org.servicestation.resources;

import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.OrderDto;
import org.servicestation.resources.exceptions.OrderNotFoundException;

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
    FullOrderDto createNewOrder(@Context SecurityContext securityContext,
                                FullOrderDto orderDto);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{stationId}/{startDateTimestamp}/{endDateTimestamp}")
    List<FullOrderDto> getAllOrdersByStationIdAndDate(@PathParam("stationId") Integer stationId,
                                                      @PathParam("startDateTimestamp") String startDateTimeStamp,
                                                      @PathParam("endDateTimestamp") String endDateTimestamp);

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{orderId}")
    FullOrderDto changeOrderById(@Context SecurityContext securityContext,
                                 @PathParam("orderId") Long orderId,
                                 FullOrderDto orderDto) throws OrderNotFoundException;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<FullOrderDto> getAllOrderByUsername(@Context SecurityContext securityContext);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{orderId}")
    FullOrderDto getOrderById(@Context SecurityContext securityContext, @PathParam("orderId") Long orderId) throws OrderNotFoundException;
}
