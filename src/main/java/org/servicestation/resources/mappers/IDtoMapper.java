package org.servicestation.resources.mappers;

public interface IDtoMapper<D, S> {

    S mapDtoToServerObject(D dto);

    D mapServerObjectToDto(S serverObj);

    Class getDtoType();

    Class getServerObjectType();
}
