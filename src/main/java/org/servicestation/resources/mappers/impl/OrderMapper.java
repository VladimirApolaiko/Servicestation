package org.servicestation.resources.mappers.impl;

import org.servicestation.model.Order;
import org.servicestation.model.OrderService;
import org.servicestation.model.Status;
import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.OrderDto;
import org.servicestation.resources.mappers.IDtoMapper;
import org.servicestation.resources.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper implements IDtoMapper<FullOrderDto, Order> {

    @Override
    public Order mapDtoToServerObject(FullOrderDto dto) {
        Order order = new Order();
        order.work_description = dto.workDescription;
        order.status = Status.valueOf(dto.status);
        order.planned_cost = dto.plannedCost;
        order.planned_end_date = Utils.getLocalDate(dto.plannedEndDate);
        order.total_cost = dto.totalCost;
        order.end_date = Utils.getLocalDate(dto.endDate);
        order.car_id = dto.carInfo.id;
        order.station_id = dto.stationId;
        order.order_date_time = Utils.getLocalDateTime(dto.orderDate);

        return order;
    }

    @Override
    public FullOrderDto mapServerObjectToDto(Order order) {
        FullOrderDto dto = new FullOrderDto();
        dto.id = order.id;
        dto.workDescription = order.work_description;
        dto.status = order.status.toString();
        dto.plannedCost = order.planned_cost;
        dto.plannedEndDate = Utils.getStringLocalDateFormat(order.planned_end_date);
        dto.totalCost = order.total_cost;
        dto.endDate = Utils.getStringLocalDateFormat(order.end_date);
        dto.stationId = order.station_id;
        dto.orderDate = Utils.getStringLocalDateTimeFormat(order.order_date_time);

        return dto;
    }

    @Override
    public Class getDtoType() {
        return FullOrderDto.class;
    }

    @Override
    public Class getServerObjectType() {
        return Order.class;
    }

}
