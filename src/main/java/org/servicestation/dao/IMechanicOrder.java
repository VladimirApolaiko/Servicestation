package org.servicestation.dao;

public interface IMechanicOrder {
    void assignOrder(final Integer mechanicId, final Long orderId);
}
