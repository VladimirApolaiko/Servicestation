package org.servicestation.resources.impl;

import org.servicestation.resources.IOrderResource;
import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.exceptions.OrderNotFoundException;
import org.servicestation.resources.managers.IOrderManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Context;
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
    public List<FullOrderDto> getAllOrdersByStationIdAndDate(Integer stationId, String startDateTimestamp, String endDateTimestamp, String status) {
        return orderManager.getOrdersByStationId(stationId, startDateTimestamp, endDateTimestamp, status);
    }

    @Override
    public List<FullOrderDto> getAllOrdersByUsernameAndDate(SecurityContext securityContext, String startDateTimeStamp, String endDateTimestamp, String status) {
        return orderManager.getOrdersByUsername(securityContext.getUserPrincipal().getName(), startDateTimeStamp, endDateTimestamp, status);
    }

    @Override
    public FullOrderDto changeOrderById(SecurityContext securityContext, Long orderId, FullOrderDto dto) throws OrderNotFoundException {
        return orderManager.changeOrder(securityContext.getUserPrincipal().getName(), orderId, dto);
    }

    @Override
    public List<FullOrderDto> getAllOrderByUsername(SecurityContext securityContext) {
        return orderManager.getOrdersByUsername(securityContext.getUserPrincipal().getName());
    }

    @Override
    public FullOrderDto getOrderById(SecurityContext securityContext, Long orderId) throws OrderNotFoundException {
        return orderManager.getOrderById(securityContext.getUserPrincipal().getName(), orderId);
    }


}
