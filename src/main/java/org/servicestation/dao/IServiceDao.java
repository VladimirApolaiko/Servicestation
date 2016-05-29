package org.servicestation.dao;

import org.servicestation.model.Service;

import java.util.List;

public interface IServiceDao {
    Service createNewService(Service newService);

    void deleteService(Integer serviceId);

    List<Service> getAllServices();

    Service getServiceById(Integer serviceId);

    Service updateService(Integer serviceId, Service newService);
}
