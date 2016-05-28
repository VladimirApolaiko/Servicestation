package org.servicestation.dao;

import org.servicestation.model.StationOrder;

import java.util.List;

public interface IStationOrderDao {
    void assignOrder(final Integer stationId, final Long orderId, final String timestamp);

    void unAssignOrder(final Integer stationId, final Long orderId);

    List<StationOrder> getStationOrders(final Integer stationId);
}
