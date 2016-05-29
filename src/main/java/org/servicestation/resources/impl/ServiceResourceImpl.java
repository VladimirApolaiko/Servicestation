package org.servicestation.resources.impl;


import org.servicestation.resources.IServiceResource;
import org.servicestation.resources.dto.ServiceDto;
import org.servicestation.resources.managers.IServiceManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;
import java.util.List;

public class ServiceResourceImpl implements IServiceResource {

    @Autowired
    private IServiceManager serviceManager;

    @Override
    public ServiceDto createNewService(ServiceDto newService) {
        return serviceManager.createNewService(newService);
    }

    @Override
    public Response deleteService(Integer serviceId) {
        serviceManager.deleteService(serviceId);
        return Response.ok().build();
    }

    @Override
    public List<ServiceDto> getAllServices() {
        return serviceManager.getAllServices();
    }

    @Override
    public ServiceDto getServiceById(Integer serviceId) {
        return serviceManager.getServiceById(serviceId);
    }

    @Override
    public ServiceDto updateService(Integer serviceId, ServiceDto newService) {
        return serviceManager.updateService(serviceId, newService);
    }
}
