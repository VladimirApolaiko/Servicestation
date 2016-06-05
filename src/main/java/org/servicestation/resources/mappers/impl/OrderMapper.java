package org.servicestation.resources.mappers.impl;

import org.servicestation.model.Order;
import org.servicestation.model.Status;
import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.mappers.IDtoMapper;
import org.servicestation.resources.utils.Utils;

public class OrderMapper implements IDtoMapper<FullOrderDto, Order> {

    @Override
    public Order mapDtoToServerObject(FullOrderDto dto) {
        Order order = new Order();
        order.status = Status.valueOf(dto.status);
        order.planned_cost = dto.planned_cost;
        order.planned_end_date = Utils.getLocalDateTime(dto.planned_end_date);
        order.total_cost = dto.total_cost;
        order.end_date = Utils.getLocalDateTime(dto.end_date);
        order.work_description = dto.work_description;

        return order;
    }

    @Override
    public FullOrderDto mapServerObjectToDto(Order serverObj) {
        FullOrderDto orderDto = new FullOrderDto();
        orderDto.id = serverObj.id;
        orderDto.status = serverObj.status.toString();
        orderDto.planned_cost = serverObj.planned_cost;
        orderDto.planned_end_date = Utils.getStringLocalDateTimeFormat(serverObj.planned_end_date);
        orderDto.total_cost = serverObj.total_cost;
        orderDto.end_date = Utils.getStringLocalDateTimeFormat(serverObj.end_date);
        orderDto.work_description = serverObj.work_description;

        return orderDto;
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
