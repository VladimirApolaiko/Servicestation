package org.servicestation.model;

import java.util.Date;

public class Order {
    public int id;
    public Date initialDate;
    public String workDescription;
    public Status status;
    public double cost;
    public Date plannedEndDate;
    public double totalCost;
    public Date endDate;
}
