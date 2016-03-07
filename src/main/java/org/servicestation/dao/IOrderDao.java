package org.servicestation.dao;

import org.servicestation.model.Order;

import java.util.List;

public interface IOrderDao {

    Order createNewOrder();

    Order changeOrder(final Integer orderId, final Order newOrder) throws Exception;

    Order deleteOrder(final Integer orderId);

    Order getOrderById(final Integer orderId);

    List<Order> getAllOrders(final Integer stationId);
}
