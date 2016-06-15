package org.servicestation.resources;

import org.servicestation.resources.dto.MechanicDto;

import javax.ws.rs.*;
import java.util.List;

@Path("/mechanic")
public interface IMechanicResource {

    @POST
    MechanicDto createMechanic(final MechanicDto dto);

    @GET
    @Path("/{mechanicId}")
    MechanicDto getMechanicById(@PathParam("mechanicId") Integer mechanicId);

    @DELETE
    @Path("/{mechanicId}")
    void deleteMechanicById(@PathParam("mechanicId") Integer mechanicId);

    @PUT
    @Path("/{mechanicId}")
    MechanicDto changeMechanicById(@PathParam("mechanicId") Integer mechanicId, MechanicDto newMechanic);

    @GET
    @Path("station/{stationId}")
    List<MechanicDto> getAllMechanicsByStationId(@PathParam("stationId") Integer stationId);

}
