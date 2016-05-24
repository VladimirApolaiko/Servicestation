package org.servicestation.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.servicestation.config.provider.JsonObjectMapperProvider;
import org.servicestation.resources.impl.EmailVerificationResourceImpl;
import org.servicestation.resources.impl.PasswordRecoveryResourceImpl;
import org.servicestation.resources.impl.TestResourceImpl;
import org.servicestation.resources.impl.UserResourceImpl;

public class JerseyResourceConfig extends ResourceConfig {

    public JerseyResourceConfig() {
        register(TestResourceImpl.class);
        register(JsonObjectMapperProvider.class);
        register(UserResourceImpl.class);
        register(EmailVerificationResourceImpl.class);
        register(PasswordRecoveryResourceImpl.class);
    }
}
