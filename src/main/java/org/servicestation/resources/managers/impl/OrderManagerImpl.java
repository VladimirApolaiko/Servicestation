package org.servicestation.resources.managers.impl;

import org.servicestation.resources.dto.OrderDto;
import org.servicestation.resources.managers.IOrderManager;

import java.time.LocalDateTime;
import java.util.List;

public class OrderManagerImpl implements IOrderManager{

    @Override
    public OrderDto createNewOrder(Integer stationId, List<Integer> servicesIds, Integer carId, LocalDateTime dateTime, String descriptionText) {
        return null;
    }

    @Override
    public OrderDto changeOrder(OrderDto newOrder) {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) {

    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return null;
    }
}
