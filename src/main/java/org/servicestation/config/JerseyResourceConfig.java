package org.servicestation.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.servicestation.resources.impl.TestResourceImpl;

public class JerseyResourceConfig extends ResourceConfig {

    public JerseyResourceConfig() {
        register(TestResourceImpl.class);
    }
}
