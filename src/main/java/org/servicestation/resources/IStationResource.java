package org.servicestation.resources;

import org.servicestation.model.Station;
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
    Response createNewStation(@Context SecurityContext securityContext, Station station);

    @Path("/{stationId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateStation(@Context SecurityContext securityContext, Station station, @PathParam("stationId") Integer stationId) throws Exception;

    @Path("/index")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    StationDto getAllIndexStations(@Context SecurityContext securityContext);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Station> getAllStations(@Context SecurityContext securityContext);

    @Path("/{stationId}")
    @DELETE
    Response deleteStation(@Context SecurityContext securityContext, @PathParam("stationId") Integer stationId) throws StationDoesNotExists;
}
