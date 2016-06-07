package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IOrderDao;
import org.servicestation.dao.IStationDao;
import org.servicestation.model.Order;
import org.servicestation.resources.dto.BusyTime;
import org.servicestation.resources.dto.StationWorkTime;
import org.servicestation.resources.managers.ITimeManager;
import org.servicestation.resources.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeManagerImpl implements ITimeManager {

    @Value("${orderRange}")
    private Integer orderRange;

    @Autowired
    private IOrderDao stationOrderDao;

    @Autowired
    private  IStationDao stationDao;

    @Override
    public BusyTime getBusyTime(Integer stationId, String timestamp) {
        BusyTime busyTime = new BusyTime();

        busyTime.busyTime.addAll(
                stationOrderDao.getOrdersByStationAndDate(stationId, Utils.getLocalDate(timestamp)).stream()
                        .map(order -> createBusyTime(order.order_date_time))
                        .collect(Collectors.toList()));

        return busyTime;
    }

    @Override
    public StationWorkTime getStationWorkTime(Integer stationId, String timestamp) {
        LocalDate date = Utils.getLocalDate(timestamp);
        if(date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7) {
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
