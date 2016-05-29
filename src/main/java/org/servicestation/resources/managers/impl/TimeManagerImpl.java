package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IStationDao;
import org.servicestation.dao.IStationOrderDao;
import org.servicestation.model.StationOrder;
import org.servicestation.resources.dto.BusyTime;
import org.servicestation.resources.dto.StationWorkTime;
import org.servicestation.resources.managers.ITimeManager;
import org.servicestation.resources.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeManagerImpl implements ITimeManager {

    @Value("${orderRange}")
    private Integer orderRange;

    @Autowired
    private IStationOrderDao stationOrderDao;

    @Autowired
    private  IStationDao stationDao;

    @Override
    public BusyTime getBusyTime(Integer stationId, String timestamp) {
        BusyTime busyTime = new BusyTime();

        for (StationOrder stationOrder : stationOrderDao.getStationOrders(stationId, Utils.getLocalDate(timestamp))) {
            busyTime.busyTime.add(createBusyTime(stationOrder.localDateTime));
        }
        return busyTime;
    }

    @Override
    public StationWorkTime getStationWorkTime(Integer stationId) {
        if(LocalDateTime.now().getDayOfWeek().getValue() == 6 || LocalDateTime.now().getDayOfWeek().getValue() == 7) {
            return Utils.transformWorkingTimeToTimeFormat(stationDao.getStationById(stationId).weekends_working_hours);
        }else {
            return Utils.transformWorkingTimeToTimeFormat(stationDao.getStationById(stationId).working_hours);
        }
    }

    private List<String> createBusyTime(LocalDateTime localDateTime) {
        List<String> busyTime = new ArrayList<>();
        busyTime.add(Utils.getTime(localDateTime));

        LocalDateTime localDateTimeWithOrderRange = localDateTime.plusMinutes(orderRange);

        busyTime.add(Utils.getTime(localDateTimeWithOrderRange));

        return busyTime;
    }



}
