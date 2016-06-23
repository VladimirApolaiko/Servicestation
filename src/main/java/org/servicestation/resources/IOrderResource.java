package org.servicestation.resources;

import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.OrderDto;
import org.servicestation.resources.exceptions.CarNotFoundException;
import org.servicestation.resources.exceptions.OrderNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.List;

@Path("/order")
public interface IOrderResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    FullOrderDto createNewOrder(@Context SecurityContext securityContext,
                                FullOrderDto orderDto) throws IOException, CarNotFoundException;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{orderId}")
    FullOrderDto changeOrderById(@PathParam("orderId") Long orderId,
                                 FullOrderDto orderDto) throws OrderNotFoundException, IOException;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<FullOrderDto> getAllOrderByUsername(@Context SecurityContext securityContext);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{orderId}")
    FullOrderDto getOrderById(@Context SecurityContext securityContext, @PathParam("orderId") Long orderId) throws OrderNotFoundException;
}
