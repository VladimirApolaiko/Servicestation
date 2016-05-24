package org.servicestation.resources;

import org.servicestation.model.User;
import org.servicestation.resources.exceptions.UserAlreadyExists;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.exceptions.ValidationException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
public interface IUserResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void createNewUser(User user) throws ValidationException, UserAlreadyExists, UserDoesNotExists;
}
