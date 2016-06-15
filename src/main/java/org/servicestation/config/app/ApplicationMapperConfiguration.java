package org.servicestation.config.app;

import org.servicestation.resources.mappers.IDtoMapper;
import org.servicestation.resources.mappers.IObjectMapper;
import org.servicestation.resources.mappers.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationMapperConfiguration {

    @Bean
    public IObjectMapper mapperManager() {
        return new ObjectMapperImpl();
    }

    @Bean
    public IDtoMapper carDtoMapper() {
        return new CarDtoMapper();
    }

    @Bean
    public IDtoMapper stationDtoMapper() {
        return new StationDtoMapper();
    }

    @Bean
    public IDtoMapper userDtoMapper() {
        return new UserDtoMapper();
    }

    @Bean
    public IDtoMapper serviceDtoMapper() {
        return new ServiceDtoMapper();
    }

    @Bean
    public IDtoMapper orderDtoMapper() {
        return new OrderMapper();
    }

    @Bean
    public IDtoMapper mechanicDtoMapper() {
        return new MechanicMapper();
    }


}
