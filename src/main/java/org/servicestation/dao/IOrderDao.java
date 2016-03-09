package org.servicestation.dao;

import org.servicestation.model.Order;

import java.util.List;

public interface IOrderDao {

    Order createNewOrder();

    Order changeOrder(final Long orderId, final Order newOrder) throws Exception;

    Order deleteOrder(final Long orderId);

    Order getOrderById(final Long orderId);

    List<Order> getAllOrders(final Integer stationId);
}
