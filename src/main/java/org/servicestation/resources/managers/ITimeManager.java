package org.servicestation.resources.managers;

import org.servicestation.resources.dto.BusyTime;
import org.servicestation.resources.dto.StationWorkTime;

public interface ITimeManager {
    BusyTime getBusyTime(Integer stationId, String timestamp);

    StationWorkTime getStationWorkTime(Integer stationId, String timestamp);

}
