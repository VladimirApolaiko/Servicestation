package org.servicestation.resources;

import org.servicestation.resources.dto.PasswordRecoveryDto;
import org.servicestation.resources.exceptions.PasswordRecoveryTokenException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/passwordrecover")
public interface IPasswordRecoveryResource {

    @GET
    @Path("/reset/{username}")
    Response resetPassword(@PathParam("username") String username);

    @POST
    @Path("/set")
    @Consumes(MediaType.APPLICATION_JSON)
    Response setPassword(PasswordRecoveryDto passwordRecovery) throws PasswordRecoveryTokenException;
}
