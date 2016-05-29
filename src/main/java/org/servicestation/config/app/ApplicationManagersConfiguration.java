package org.servicestation.config.app;

import org.apache.velocity.app.VelocityEngine;
import org.servicestation.resources.impl.StationManagerImpl;
import org.servicestation.resources.managers.*;
import org.servicestation.resources.managers.impl.*;
import org.servicestation.resources.managers.impl.MailManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationManagersConfiguration {

    @Bean
    public IUserManager userManager() {
        return new UserManager();
    }

    @Bean
    public IAuthoritiesManager authoritiesManager() {
        return new AuthoritiesManager();
    }

    @Bean
    public IEmailVerificationManager emailVerificationManager() {
        return new EmailVerificationManagerImpl();
    }

    @Bean
    public ICarManager carManager() {
        return new CarManagerImpl();
    }

    @Bean
    public IPasswordRecoveryManager passwordRecoveryManager() {
        return new PasswordRecoveryManagerImpl();
    }

    @Bean
    public IStationManager stationManager() {
        return new StationManagerImpl();
    }

    @Bean
    public IStationOrderManager stationOrderManager() {
        return new StationOrderManagerImpl();
    }

    @Bean
    public ITimeManager timeManagerImpl() {
        return new TimeManagerImpl();
    }

    @Bean
    public VelocityEngine velocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class", " org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return velocityEngine;
    }

    @Bean
    public MailManager mailManager() {
        return new MailManager();
    }
}
