package org.servicestation.resources.managers;

import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.OrderDto;

import java.time.LocalDate;
import java.util.List;

public interface IOrderManager {
    FullOrderDto createNewOrder(String username, FullOrderDto orderDto);

    OrderDto changeOrder(Long orderId, OrderDto newOrder);

    void deleteOrder(Long orderId);

    OrderDto getOrderById(Long orderId);

    List<OrderDto> getOrdersByStationId(Integer stationId, String timestamp);
}
