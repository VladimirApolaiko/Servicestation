package org.servicestation.resources.managers;

import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.OrderDto;

import java.util.List;

public interface IOrderManager {
    FullOrderDto createNewOrder(String username, FullOrderDto orderDto);

    FullOrderDto changeOrder(Long orderId, FullOrderDto newOrder);

    void deleteOrder(Long orderId);

    FullOrderDto getOrderById(Long orderId);

    List<FullOrderDto> getOrdersByStationId(Integer stationId, String timestamp);
}
