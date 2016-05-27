package org.servicestation.resources.mappers.impl;

import org.servicestation.resources.mappers.IDtoMapper;
import org.servicestation.resources.mappers.MapperManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class MapperManagerImpl implements MapperManager{

    private Map<Class, IDtoMapper> mappers = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public <T, E> T mapDtoToServerObject(E object) {
        return (T) mappers.get(object.getClass()).mapDtoToServerObject(object);
    }

    @Override
    public <E, T> E mapServerObjectToDto(T object) {
        return (E) mappers.get(object.getClass()).mapServerObjectToDto(object);
    }

    @PostConstruct
    public void initialize() {
        for(IDtoMapper mapper : applicationContext.getBeansOfType(IDtoMapper.class).values()) {
            mappers.put(mapper.getDtoType(), mapper);
            mappers.put(mapper.getServerObjectType(), mapper);
        }

    }
}
