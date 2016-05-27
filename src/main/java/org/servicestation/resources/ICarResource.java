package org.servicestation.resources;

import org.servicestation.resources.dto.CarDto;
import org.servicestation.resources.exceptions.CarNotFoundException;
import org.servicestation.resources.exceptions.UserDoesNotExists;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/car")
public interface ICarResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    CarDto createNewCar(@Context SecurityContext securityContext, CarDto car) throws UserDoesNotExists;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<CarDto> getCars(@Context SecurityContext securityContext) throws UserDoesNotExists, CarNotFoundException;

    @PUT
    @Path("/{carId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    CarDto updateCar(@Context SecurityContext securityContext, CarDto car, @PathParam("carId") Integer carId) throws Exception;

    @DELETE
    @Path("/{carId}")
    Response deleteCar(@Context SecurityContext securityContext, @PathParam("carId") Integer carId) throws UserDoesNotExists, CarNotFoundException;

}
