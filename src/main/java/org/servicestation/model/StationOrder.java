package org.servicestation.model;


import java.time.LocalDateTime;

public class StationOrder {
    public Long id;
    public Integer stationId;
    public Long orderId;
    public Integer carId;
    public LocalDateTime localDateTime;
}
