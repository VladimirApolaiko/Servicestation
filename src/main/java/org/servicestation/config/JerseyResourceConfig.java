package org.servicestation.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.servicestation.config.provider.JsonObjectMapperProvider;
import org.servicestation.resources.impl.*;
import org.servicestation.resources.managers.impl.StationResourceImpl;

public class JerseyResourceConfig extends ResourceConfig {

    public JerseyResourceConfig() {
        register(TestResourceImpl.class);
        register(CarResourceImpl.class);
        register(StationResourceImpl.class);
        register(JsonObjectMapperProvider.class);
        register(UserResourceImpl.class);
        register(EmailVerificationResourceImpl.class);
        register(PasswordRecoveryResourceImpl.class);
    }
}
