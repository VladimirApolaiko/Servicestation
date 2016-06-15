package org.servicestation.dao;

import org.servicestation.model.Order;
import org.servicestation.model.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrderDao {

    Order createNewOrder(Status status, String username, Integer stationId, LocalDateTime orderDateTime, Integer carId);

    Order changeOrder(final Long orderId, final Order newOrder);

    Order deleteOrder(final Long orderId);

    Order getOrderById(final Long orderId);

    List<Order> getOrdersByStationId(final Integer stationId);

    List<Order> getOrdersByStationId(final Integer stationId, final Status status);

    List<Order> getOrdersByStationId(final Integer stationId, final LocalDate startDateTimestamp, final LocalDate endDateTimestamp);

    List<Order> getOrdersByUsername(final String username);

    List<Order> getOrdersByUsername(final String username, final Status status);

    List<Order> getOrdersByUsername(final String username, final LocalDate startDateTimestamp, final LocalDate endDateTimestamp);

}
