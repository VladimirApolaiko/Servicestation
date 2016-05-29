package org.servicestation.resources.managers;


import org.servicestation.resources.dto.StationOrderDto;

import java.util.List;

public interface IStationOrderManager {

    StationOrderDto createStationOrder(Integer stationId, StationOrderDto newStationOrder );

    void deleteStationOrder(Integer stationId, Long orderId);

    List<StationOrderDto> getAllStationOrders(Integer stationId, String timestamp);

}
