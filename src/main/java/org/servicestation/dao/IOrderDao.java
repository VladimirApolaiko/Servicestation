package org.servicestation.dao;

import org.servicestation.model.Order;

public interface IOrderDao {

    Order createNewOrder();

    Order changeOrder(Order newOrder);

    Order deleteOrder(int orderId);
}
