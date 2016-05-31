package org.servicestation.resources.managers;

import org.servicestation.resources.dto.OrderDto;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderManager {
    OrderDto createNewOrder(
            Integer stationId,
            List<Integer> servicesIds,
            Integer carId,
            LocalDateTime dateTime,
            String descriptionText);

    OrderDto changeOrder(OrderDto newOrder);

    void deleteOrder(Long orderId);

    OrderDto getOrderById(Long orderId);
}
