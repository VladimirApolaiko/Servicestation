package org.servicestation.dao;


import org.servicestation.model.OrderService;

import java.util.List;

public interface IOrderServiceDao {
    OrderService assignService(Long orderId, Integer serviceId);

    void unassignService(Long orderId, Integer serviceId);

    List<OrderService> getServicesByOrderId(Long orderId);
}
