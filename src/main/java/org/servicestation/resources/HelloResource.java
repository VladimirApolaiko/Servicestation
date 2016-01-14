package org.servicestation.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
public interface HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String sayHello();
}
