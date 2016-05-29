package org.servicestation.resources.managers;

import org.servicestation.resources.dto.ServiceDto;

import java.util.List;

public interface IServiceManager {
    ServiceDto createNewService(ServiceDto newService);

    void deleteService(Integer serviceId);

    List<ServiceDto> getAllServices();

    ServiceDto getServiceById(Integer serviceId);

    ServiceDto updateService(Integer serviceId, ServiceDto newService);
}
