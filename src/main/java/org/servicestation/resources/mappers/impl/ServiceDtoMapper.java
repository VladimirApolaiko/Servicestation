package org.servicestation.resources.mappers.impl;

import org.servicestation.model.Service;
import org.servicestation.resources.dto.ServiceDto;
import org.servicestation.resources.mappers.IDtoMapper;

public class ServiceDtoMapper implements IDtoMapper<ServiceDto, Service>{

    @Override
    public Service mapDtoToServerObject(ServiceDto dto) {
        Service service = new Service();
        service.service_name = dto.service_name;
        service.price = dto.price;

        return service;
    }

    @Override
    public ServiceDto mapServerObjectToDto(Service serverObj) {
        ServiceDto dto = new ServiceDto();
        dto.id = serverObj.id;
        dto.service_name = serverObj.service_name;
        dto.price = serverObj.price;

        return dto;
    }

    @Override
    public Class getDtoType() {
        return ServiceDto.class;
    }

    @Override
    public Class getServerObjectType() {
        return Service.class;
    }
}
