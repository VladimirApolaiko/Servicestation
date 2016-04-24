package org.servicestation.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.servicestation.config.provider.JsonObjectMapperProvider;
import org.servicestation.resources.impl.TestResourceImpl;
import org.servicestation.resources.impl.UserResourceImpl;

public class JerseyResourceConfig extends ResourceConfig {

    public JerseyResourceConfig() {
        register(TestResourceImpl.class);
        register(JsonObjectMapperProvider.class);
        register(UserResourceImpl.class);
    }
}
