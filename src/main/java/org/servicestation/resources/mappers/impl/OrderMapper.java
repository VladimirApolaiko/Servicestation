package org.servicestation.resources.mappers.impl;

import org.servicestation.model.Order;
import org.servicestation.model.Status;
import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.OrderDto;
import org.servicestation.resources.mappers.IDtoMapper;
import org.servicestation.resources.utils.Utils;

public class OrderMapper implements IDtoMapper<OrderDto, Order> {

    @Override
    public Order mapDtoToServerObject(OrderDto dto) {
        Order order = new Order();
        order.status = Status.valueOf(dto.status);
        order.planned_cost = dto.plannedCost;
        order.planned_end_date = Utils.getLocalDateTime(dto.plannedEndDate);
        order.total_cost = dto.totalCost;
        order.end_date = Utils.getLocalDateTime(dto.endDate);
        order.work_description = dto.workDescription;
        order.order_date_time = Utils.getLocalDateTime(dto.orderDateTime);
        order.station_id = dto.stationId;
        order.car_id = dto.carId;

        return order;
    }

    @Override
    public OrderDto mapServerObjectToDto(Order serverObj) {
        OrderDto orderDto = new OrderDto();
        orderDto.id = serverObj.id;
        orderDto.status = serverObj.status.toString();
        orderDto.plannedCost = serverObj.planned_cost;
        orderDto.plannedEndDate = Utils.getStringLocalDateTimeFormat(serverObj.planned_end_date);
        orderDto.totalCost = serverObj.total_cost;
        orderDto.endDate = Utils.getStringLocalDateTimeFormat(serverObj.end_date);
        orderDto.workDescription = serverObj.work_description;
        orderDto.orderDateTime = Utils.getStringLocalDateTimeFormat(serverObj.order_date_time);
        orderDto.stationId = serverObj.station_id;
        orderDto.carId = serverObj.car_id;

        return orderDto;
    }

    @Override
    public Class getDtoType() {
        return OrderDto.class;
    }

    @Override
    public Class getServerObjectType() {
        return Order.class;
    }
}
