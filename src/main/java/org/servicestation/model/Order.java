package org.servicestation.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
    public Long id = null;
    public String work_description = null;
    public Status status = null;
    public Double planned_cost = null;
    public LocalDate planned_end_date = null;
    public Double total_cost = null;
    public LocalDate end_date = null;
    public String username = null;
    public Integer station_id = null;
    public LocalDateTime order_date_time = null;
    public Integer car_id = null;
}
