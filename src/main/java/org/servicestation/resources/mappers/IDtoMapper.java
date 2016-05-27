package org.servicestation.resources.mappers;

public interface IDtoMapper<DTO, SERVEROBJ> {

    SERVEROBJ mapDtoToServerObject(DTO dto);

    DTO mapServerObjectToDto(SERVEROBJ serverObj);

    Class getDtoType();

    Class getServerObjectType();
}
