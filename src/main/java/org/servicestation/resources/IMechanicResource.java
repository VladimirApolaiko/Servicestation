package org.servicestation.resources;

import org.servicestation.resources.dto.MechanicDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/mechanic")
public interface IMechanicResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    MechanicDto createMechanic(final MechanicDto dto);

    @GET
    @Path("/{mechanicId}")
    @Produces(MediaType.APPLICATION_JSON)
    MechanicDto getMechanicById(@PathParam("mechanicId") Integer mechanicId);

    @DELETE
    @Path("/{mechanicId}")
    void deleteMechanicById(@PathParam("mechanicId") Integer mechanicId);

    @PUT
    @Path("/{mechanicId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    MechanicDto changeMechanicById(@PathParam("mechanicId") Integer mechanicId, MechanicDto newMechanic);

    @GET
    @Path("station/{stationId}")
    @Produces(MediaType.APPLICATION_JSON)
    List<MechanicDto> getAllMechanicsByStationId(@PathParam("stationId") Integer stationId);

}
