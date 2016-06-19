package org.servicestation.resources.managers.impl;


import org.servicestation.dao.IOrderServiceDao;
import org.servicestation.dao.IServiceDao;
import org.servicestation.model.Service;
import org.servicestation.resources.dto.ServiceDto;
import org.servicestation.resources.managers.IServiceManager;
import org.servicestation.resources.mappers.IObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceManagerImpl implements IServiceManager {

    @Autowired
    private IServiceDao serviceDao;

    @Autowired
    private IOrderServiceDao orderServiceDao;

    @Autowired
    private IObjectMapper mapper;

    @Override
    public ServiceDto createNewService(ServiceDto newService) {
        return mapper.mapServerObjectToDto(serviceDao.createNewService(mapper.mapDtoToServerObject(newService)));
    }

    @Override
    public void deleteService(Integer serviceId) {
        serviceDao.deleteService(serviceId);
    }

    @Override
    public List<ServiceDto> getAllServices() {
        return serviceDao.getAllServices().stream()
                .map(service -> mapper.<ServiceDto, Service>mapServerObjectToDto(service))
                .collect(Collectors.toList());
    }

    @Override
    public ServiceDto getServiceById(Integer serviceId) {
        return mapper.mapServerObjectToDto(serviceDao.getServiceById(serviceId));
    }

    @Override
    public ServiceDto updateService(Integer serviceId, ServiceDto newService) {
        return mapper.mapServerObjectToDto(serviceDao.updateService(serviceId, mapper.mapDtoToServerObject(newService)));
    }
}
