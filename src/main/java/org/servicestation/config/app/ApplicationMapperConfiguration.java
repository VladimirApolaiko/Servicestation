package org.servicestation.config.app;

import org.servicestation.resources.mappers.IDtoMapper;
import org.servicestation.resources.mappers.IObjectMapper;
import org.servicestation.resources.mappers.impl.CarDtoMapper;
import org.servicestation.resources.mappers.impl.ObjectMapperImpl;
import org.servicestation.resources.mappers.impl.StationDtoMapper;
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

}
