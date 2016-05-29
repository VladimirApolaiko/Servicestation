package org.servicestation.resources.mappers.impl;

import org.servicestation.resources.IStationOrderResource;
import org.servicestation.resources.dto.StationOrderDto;
import org.servicestation.resources.managers.IStationOrderManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StationOrderResourceImpl implements IStationOrderResource {

    @Autowired
    private IStationOrderManager stationOrderManager;

    @Override
    public List<StationOrderDto> getAllStationOrders(Integer stationId, String timestamp) {
        return stationOrderManager.getAllStationOrders(stationId, timestamp);
    }


}
