package org.servicestation.resources.impl;

import org.servicestation.resources.IOrderResource;
import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.OrderDto;
import org.servicestation.resources.managers.IOrderManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.SecurityContext;
import java.util.List;

public class OrderResourceImpl implements IOrderResource {

    @Autowired
    private IOrderManager orderManager;

    @Override
    public FullOrderDto createNewOrder(SecurityContext securityContext, FullOrderDto orderDto) {
        return orderManager.createNewOrder(securityContext.getUserPrincipal().getName(), orderDto);
    }

    @Override
    public List<OrderDto> getAllOrdersByStationIdAndDate(Integer stationId, String timestamp) {
        return orderManager.getOrdersByStationId(stationId, timestamp);
    }


}
