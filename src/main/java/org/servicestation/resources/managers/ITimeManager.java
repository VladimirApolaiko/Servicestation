package org.servicestation.resources.managers;

import org.servicestation.resources.dto.BusyTime;

public interface ITimeManager {
    BusyTime getBusyTime(Integer stationId, String timestamp);
}
