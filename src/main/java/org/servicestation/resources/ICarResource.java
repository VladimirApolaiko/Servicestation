package org.servicestation.resources;

import org.servicestation.model.Car;
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
    Response createNewCar(@Context SecurityContext securityContext, Car car) throws UserDoesNotExists;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Car> getCars(@Context SecurityContext securityContext) throws UserDoesNotExists, CarNotFoundException;

    @PUT
    @Path("/{carId}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateCar(@Context SecurityContext securityContext, Car car, @PathParam("carId") Integer carId) throws Exception;

    @DELETE
    @Path("/{carId}")
    Response deleteCar(@Context SecurityContext securityContext, @PathParam("carId") Integer carId) throws UserDoesNotExists, CarNotFoundException;

}
