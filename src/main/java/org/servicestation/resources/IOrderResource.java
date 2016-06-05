package org.servicestation.resources;

import org.servicestation.resources.dto.FullOrderDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IOrderResource {

    @POST
    FullOrderDto createNewOrder(FullOrderDto orderDto);


}
