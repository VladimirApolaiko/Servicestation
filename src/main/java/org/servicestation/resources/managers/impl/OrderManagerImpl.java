package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IOrderDao;
import org.servicestation.dao.IOrderServiceDao;
import org.servicestation.dao.IStationOrderDao;
import org.servicestation.model.Order;
import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.managers.IOrderManager;
import org.servicestation.resources.mappers.IObjectMapper;
import org.servicestation.resources.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderManagerImpl implements IOrderManager {

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IStationOrderDao stationOrderDao;

    @Autowired
    private IOrderServiceDao orderServiceDao;

    @Autowired
    private IObjectMapper mapper;

    @Override
    public FullOrderDto createNewOrder(FullOrderDto orderDto) {
        Order newOrder = orderDao.createNewOrder();
        newOrder.work_description = orderDto.work_description;
        orderDao.changeOrder(newOrder.id, newOrder);
        stationOrderDao.assignOrder(orderDto.stationId, newOrder.id, Utils.getLocalDateTime(orderDto.order_date), orderDto.carId);

        for (Integer serviceId : orderDto.servicesIds) {
            orderServiceDao.assignService(newOrder.id, serviceId);
        }

        return orderDto;
    }

    @Override
    public FullOrderDto changeOrder(Long orderId, FullOrderDto newOrder) {
        return mapper.mapServerObjectToDto(orderDao.changeOrder(orderId, mapper.mapDtoToServerObject(newOrder)));
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderDao.deleteOrder(orderId);
    }

    @Override
    public FullOrderDto getOrderById(Long orderId) {
        return mapper.mapServerObjectToDto(orderDao.getOrderById(orderId));
    }
}
