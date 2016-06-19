package org.servicestation.resources;

import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.managers.Authority;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/authority")
public interface IAuthoritiesResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Authority> getAuthorities(@Context SecurityContext securityContext) throws UserDoesNotExists;
}
