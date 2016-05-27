package org.servicestation.resources.mappers;


public interface MapperManager {
    <T,E> T mapDtoToServerObject(E object);

    <E, T> E mapServerObjectToDto(T object);
}
