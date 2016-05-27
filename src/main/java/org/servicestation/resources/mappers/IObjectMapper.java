package org.servicestation.resources.mappers;


public interface IObjectMapper {
    <D, S> S mapDtoToServerObject(D object);

    <D, S> D mapServerObjectToDto(S object);
}
