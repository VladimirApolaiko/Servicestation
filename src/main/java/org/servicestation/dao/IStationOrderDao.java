package org.servicestation.dao;

import org.servicestation.model.StationOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IStationOrderDao {
    void assignOrder(final Integer stationId, final Long orderId, final LocalDateTime timestamp, final Integer carId);

    void unAssignOrder(final Integer stationId, final Long orderId);

    List<StationOrder> getStationOrders(final Integer stationId, final LocalDate localDate);
}
