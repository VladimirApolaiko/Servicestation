package org.servicestation.resources;

import org.servicestation.resources.dto.UserDto;
import org.servicestation.resources.exceptions.UserAlreadyExists;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.exceptions.ValidationException;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/user")
public interface IUserResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UserDto createNewUser(UserDto user) throws ValidationException, UserAlreadyExists, UserDoesNotExists;

    @PUT
    @PreAuthorize("hasRole('ROLE_USER')")
    UserDto changeUser(UserDto user, @Context SecurityContext securityContext) throws UserDoesNotExists;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("hasRole('ROLE_USER')")
    UserDto getUser(@Context SecurityContext securityContext) throws UserDoesNotExists;

}
