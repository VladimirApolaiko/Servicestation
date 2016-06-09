package org.servicestation.resources.dto;


import java.util.ArrayList;
import java.util.List;

public class FullOrderDto {
    public Long id = null;
    public String workDescription = null;
    public String status = null;
    public Double plannedCost = null;
    public String plannedEndDate = null;
    public Double totalCost = null;
    public String endDate = null;
    public List<Integer> servicesIds = new ArrayList<>();
    public Integer carId = null;
    public Integer stationId = null;
    public String orderDate = null;
}
