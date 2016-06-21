package org.servicestation.config.app;

import org.servicestation.resources.*;
import org.servicestation.resources.impl.*;
import org.servicestation.resources.managers.impl.StationResourceImpl;
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
    public ITimeResource timeResource() {
        return new TimeResourceImpl();
    }

    @Bean
    public IOrderResource orderResource() {
        return new OrderResourceImpl();
    }

    @Bean
    public IServiceResource serviceResource() {
        return new ServiceResourceImpl();
    }

    @Bean
    public IMechanicResource mechanicResource() {
        return new MechanicResourceImpl();
    }

    @Bean
    public IAdminResource adminResource() {
        return new AdminResourceImpl();
    }
}
