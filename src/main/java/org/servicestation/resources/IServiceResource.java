package org.servicestation.resources;

import org.servicestation.resources.dto.ServiceDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/service")
public interface IServiceResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    ServiceDto createNewService(ServiceDto newService);

    @DELETE
    @Path("/{serviceId}")
    Response deleteService(@PathParam("serviceId") Integer serviceId);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<ServiceDto> getAllServices();

    @GET
    @Path("/{serviceId}")
    @Produces(MediaType.APPLICATION_JSON)
    ServiceDto getServiceById(@PathParam("serviceId") Integer serviceId);

    @PUT
    @Path("{serviceId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    ServiceDto updateService(@PathParam("serviceId") Integer serviceId, ServiceDto newService);
}
