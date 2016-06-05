package org.servicestation.resources.managers;


import org.servicestation.resources.dto.StationOrderDto;

import java.util.List;

public interface IStationOrderManager {

    List<StationOrderDto> getAllStationOrders(Integer stationId, String timestamp);
}
