package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IStationOrderDao;
import org.servicestation.resources.dto.StationOrderDto;
import org.servicestation.resources.managers.IStationOrderManager;
import org.servicestation.resources.mappers.IObjectMapper;
import org.servicestation.resources.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StationOrderManagerImpl implements IStationOrderManager {

    @Autowired
    private IStationOrderDao stationOrderDao;

    @Autowired
    private IObjectMapper mapper;

    @Override
    public StationOrderDto createStationOrder(Integer stationId, StationOrderDto newStationOrder) {
        return null;
    }

    @Override
    public void deleteStationOrder(Integer stationId, Long orderId) {

    }

    @Override
    public List<StationOrderDto> getAllStationOrders(Integer stationId, String timestamp) {
        return mapper.mapServerObjectToDto(stationOrderDao.getStationOrders(stationId, Utils.getLocalDate(timestamp)));
    }

}
