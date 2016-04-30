package org.servicestation.model;

import java.time.LocalDate;

public class Order {
    public Long id = null;
    public LocalDate initial_date = null;
    public String work_description = null;
    public Status status = null;
    public Double planned_cost = null;
    public LocalDate planned_end_date = null;
    public Double total_cost = null;
    public LocalDate end_date = null;
    public Long station_id = null;
}
