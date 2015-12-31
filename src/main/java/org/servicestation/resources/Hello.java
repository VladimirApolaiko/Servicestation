package org.servicestation.resources;

import javax.activation.MimeType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vladimir on 31.12.2015.
 */
@Path("/")
public class Hello {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "hello world";
    }
}
