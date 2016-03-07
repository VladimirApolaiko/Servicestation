package org.servicestation.dao;

import org.servicestation.model.Order;

import java.util.List;

public interface IMechanicOrder {
    void assignOrder(final Integer mechanicId, final Long orderId);

    List<Order> getMechanicOrders(final Integer mechanicId);

}
