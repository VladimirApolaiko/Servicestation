package org.servicestation.resources;

import org.servicestation.dao.exceptions.NullProperiesException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
public interface TestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String test() throws Exception;
}
