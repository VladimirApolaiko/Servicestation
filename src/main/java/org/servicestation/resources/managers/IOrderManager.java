package org.servicestation.resources.managers;

import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.OrderDto;
import org.servicestation.resources.exceptions.CarNotFoundException;
import org.servicestation.resources.exceptions.OrderNotFoundException;

import java.io.IOException;
import java.util.List;

public interface IOrderManager {
    FullOrderDto createNewOrder(String username, FullOrderDto orderDto) throws IOException, CarNotFoundException;

    FullOrderDto changeOrder(Long orderId, FullOrderDto newOrder) throws OrderNotFoundException, IOException;

    void deleteOrder(Long orderId) throws IOException;

    FullOrderDto getOrderById(String username, Long orderId) throws OrderNotFoundException;

    List<FullOrderDto> getOrdersByStationId(Integer stationId);

    List<FullOrderDto> getOrdersByStationId(Integer stationId, String status);

    List<FullOrderDto> getOrdersByStationId(Integer stationId, String startDateTimestamp, String endDateTimestamp);

    List<FullOrderDto> getOrdersByUsername(String username);

    List<FullOrderDto> getOrdersByUsername(String username, String status);

    List<FullOrderDto> getOrdersByUsername(String username, String startDateTimestamp, String endDateTimestamp);
}
