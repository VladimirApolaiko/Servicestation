package org.servicestation.config.app;

import org.servicestation.resources.IEmailVerificationResource;
import org.servicestation.resources.IPasswordRecoveryResource;
import org.servicestation.resources.IServiceResource;
import org.servicestation.resources.ITimeResource;
import org.servicestation.resources.impl.*;
import org.servicestation.resources.managers.impl.StationResourceImpl;
import org.servicestation.resources.mappers.impl.StationOrderResourceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationResourceConfiguration {

    @Bean
    public TestResourceImpl helloResource() {
        return new TestResourceImpl();
    }

    @Bean
    public CarResourceImpl carResource() {
        return new CarResourceImpl();
    }

    @Bean
    public UserResourceImpl userResource() {
        return new UserResourceImpl();
    }

    @Bean
    public IEmailVerificationResource emailVerificationResource() {
        return new EmailVerificationResourceImpl();
    }

    @Bean
    public IPasswordRecoveryResource passwordRecoveryResource() {
        return new PasswordRecoveryResourceImpl();
    }

    @Bean
    public StationResourceImpl stationResource() {
        return new StationResourceImpl();
    }

    @Bean
    public StationOrderResourceImpl stationOrderResource() {
        return new StationOrderResourceImpl();
    }

    @Bean
    public ITimeResource timeResource() {
        return new TimeResourceImpl();
    }

    @Bean
    public IServiceResource serviceResource() {
        return new ServiceResourceImpl();
    }
}
