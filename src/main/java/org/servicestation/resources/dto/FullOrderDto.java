package org.servicestation.resources.dto;


import java.util.ArrayList;
import java.util.List;

public class FullOrderDto {
    public Long id = null;
    public String work_description = null;
    public String status = null;
    public Double planned_cost = null;
    public String planned_end_date = null;
    public Double total_cost = null;
    public String end_date = null;
    public List<Integer> servicesIds = new ArrayList<>();
    public Integer carId = null;
    public Integer stationId = null;
    public String order_date = null;
}
