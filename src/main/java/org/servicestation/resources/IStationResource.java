package org.servicestation.resources;

import org.servicestation.resources.dto.MapStationDto;
import org.servicestation.resources.dto.StationDto;
import org.servicestation.resources.exceptions.StationDoesNotExists;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/station")
public interface IStationResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    StationDto createNewStation(@Context SecurityContext securityContext, StationDto station);

    @Path("/{stationId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    StationDto updateStation(@Context SecurityContext securityContext, StationDto station, @PathParam("stationId") Integer stationId) throws Exception;

    @Path("/index")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    MapStationDto getAllIndexStations(@Context SecurityContext securityContext);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<StationDto> getAllStations(@Context SecurityContext securityContext);

    @Path("/{stationId}")
    @DELETE
    Response deleteStation(@Context SecurityContext securityContext, @PathParam("stationId") Integer stationId) throws StationDoesNotExists;
}
