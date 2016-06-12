package org.servicestation.resources.managers;

import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.OrderDto;
import org.servicestation.resources.exceptions.OrderNotFoundException;

import java.util.List;

public interface IOrderManager {
    FullOrderDto createNewOrder(String username, FullOrderDto orderDto);

    FullOrderDto changeOrder(String username, Long orderId, FullOrderDto newOrder) throws OrderNotFoundException;

    void deleteOrder(Long orderId);

    FullOrderDto getOrderById(String username, Long orderId) throws OrderNotFoundException;

    List<FullOrderDto> getOrdersByStationId(Integer stationId, String startDateTimestamp, String endDateTimestamp, String status);

    List<FullOrderDto> getOrdersByUsername(String username, String startDateTimestamp, String endDateTimestamp, String status);

    List<FullOrderDto> getOrdersByUsername(String username);
}
