package org.servicestation.dao;

public interface IStationOrderDao {
    void assignOrder(final Integer stationId, final Long orderId);

    void unAssignOrder(final Integer stationId, final Long orderId);
}
