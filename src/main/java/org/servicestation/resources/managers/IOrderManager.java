package org.servicestation.resources.managers;

import org.servicestation.resources.dto.FullOrderDto;

public interface IOrderManager {
    FullOrderDto createNewOrder(FullOrderDto orderDto);

    FullOrderDto changeOrder(Long orderId, FullOrderDto newOrder);

    void deleteOrder(Long orderId);

    FullOrderDto getOrderById(Long orderId);
}
