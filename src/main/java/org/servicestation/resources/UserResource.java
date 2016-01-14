package org.servicestation.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
public interface UserResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    String getUserById(@PathParam("id") Integer id);
}
