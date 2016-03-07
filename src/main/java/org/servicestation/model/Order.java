package org.servicestation.model;

import java.util.Date;

public class Order {
    public Long id = null;
    public Date initial_date = null;
    public String work_description = null;
    public Status status = null;
    public Double planned_cost = null;
    public Date planned_end_date = null;
    public Double total_cost = null;
    public Date end_date = null;
    public Integer station_id = null;
}
