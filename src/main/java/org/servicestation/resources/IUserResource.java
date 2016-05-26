package org.servicestation.resources;

import org.servicestation.model.User;
import org.servicestation.resources.exceptions.UserAlreadyExists;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.exceptions.ValidationException;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
public interface IUserResource {

    @POST
    void createNewUser(User user) throws ValidationException, UserAlreadyExists, UserDoesNotExists;

    @PUT
    @PreAuthorize("hasRole('ROLE_USER')")
    void changeUser(User user, @Context SecurityContext securityContext) throws UserDoesNotExists;

}
