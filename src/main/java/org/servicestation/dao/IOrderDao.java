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

    List<Order> getOrdersByStationAndDate(final Integer stationId, final LocalDate order_date);

}
