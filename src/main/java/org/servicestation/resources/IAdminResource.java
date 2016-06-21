package org.servicestation.resources;

import org.servicestation.resources.dto.UserDto;
import org.servicestation.resources.exceptions.UserAlreadyExists;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.exceptions.ValidationException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/admin")
public interface IAdminResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UserDto createNewStationAdmin(UserDto user) throws UserDoesNotExists, UserAlreadyExists, ValidationException;

    @GET
    @Path("/station")
    @Produces(MediaType.APPLICATION_JSON)
    List<UserDto> getAllStationAdmins();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UserDto getStationAdmin(@Context SecurityContext securityContext);

    @PUT
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UserDto changeAdmin(@PathParam("username") String username, UserDto newUser) throws UserDoesNotExists;

    @DELETE
    @Path("/{username}")
    void deleteAdmin(@PathParam("username") String username) throws UserDoesNotExists;
}
