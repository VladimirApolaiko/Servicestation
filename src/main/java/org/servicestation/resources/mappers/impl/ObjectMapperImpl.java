package org.servicestation.resources.mappers.impl;

import org.servicestation.resources.mappers.IDtoMapper;
import org.servicestation.resources.mappers.IObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class ObjectMapperImpl implements IObjectMapper {

    private Map<Class, IDtoMapper> mappers = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public <D, S> S mapDtoToServerObject(D object) {
        return (S) mappers.get(object.getClass()).mapDtoToServerObject(object);
    }

    @Override
    public <D, S> D mapServerObjectToDto(S object) {
        return (D) mappers.get(object.getClass()).mapServerObjectToDto(object);
    }

    @PostConstruct
    public void initialize() {
        for(IDtoMapper mapper : applicationContext.getBeansOfType(IDtoMapper.class).values()) {
            mappers.put(mapper.getDtoType(), mapper);
            mappers.put(mapper.getServerObjectType(), mapper);
        }

    }
}
