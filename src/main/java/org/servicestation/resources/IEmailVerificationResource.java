package org.servicestation.resources;

import org.servicestation.resources.exceptions.EmailConfirmationTokenException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/emailconfirm/{token}")
public interface IEmailVerificationResource {

    @GET
    Response confirmEmail(@PathParam("token") String token) throws EmailConfirmationTokenException;
}
