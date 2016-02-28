package org.servicestation.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.servicestation.resources.impl.HelloResourceImpl;

public class JerseyResourceConfig extends ResourceConfig {

    public JerseyResourceConfig() {
        register(HelloResourceImpl.class);
    }
}
