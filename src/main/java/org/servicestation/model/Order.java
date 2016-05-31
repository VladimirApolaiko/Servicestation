package org.servicestation.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
    public Long id = null;
    public String work_description = null;
    public Status status = null;
    public Double planned_cost = null;
    public LocalDateTime planned_end_date = null;
    public Double total_cost = null;
    public LocalDateTime end_date = null;
}
