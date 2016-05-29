package org.servicestation.resources.impl;


import org.servicestation.resources.ITimeResource;
import org.servicestation.resources.dto.BusyTime;
import org.servicestation.resources.dto.StationWorkTime;
import org.servicestation.resources.managers.ITimeManager;
import org.springframework.beans.factory.annotation.Autowired;

public class TimeResourceImpl implements ITimeResource{

    @Autowired
    private ITimeManager timeManager;

    @Override
    public BusyTime getBusyTime(Integer stationId, String timestamp) {
        return timeManager.getBusyTime(stationId, timestamp);
    }

    @Override
    public StationWorkTime getStationWorkTime(Integer stationId) {
        return timeManager.getStationWorkTime(stationId);
    }
}
