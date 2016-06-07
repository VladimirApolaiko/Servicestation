package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IOrderDao;
import org.servicestation.dao.IOrderServiceDao;
import org.servicestation.dao.IServiceDao;
import org.servicestation.model.Order;
import org.servicestation.model.OrderService;
import org.servicestation.model.Status;
import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.OrderDto;
import org.servicestation.resources.managers.IOrderManager;
import org.servicestation.resources.mappers.IObjectMapper;
import org.servicestation.resources.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderManagerImpl implements IOrderManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderManagerImpl.class);

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IOrderServiceDao orderServiceDao;

    @Autowired
    private IServiceDao serviceDao;

    @Autowired
    private IObjectMapper mapper;

    @Override
    public FullOrderDto createNewOrder(String username, FullOrderDto orderDto) {
        Order newOrder = orderDao.createNewOrder(
                Status.valueOf(orderDto.status), username, orderDto.stationId, Utils.getLocalDateTime(orderDto.orderDate), orderDto.carId);
        newOrder.work_description = orderDto.workDescription;
        Map<Integer, Double> servicePrices =
                serviceDao.getAllServices().stream().collect(Collectors.toMap(service -> service.id, service -> service.price));

        Double plannedCost = 0.0;
        for (Integer serviceId : orderDto.servicesIds) {
            if(servicePrices.get(serviceId) != null) {
                plannedCost += servicePrices.get(serviceId);
            }else{
                LOGGER.info("Service with service id {} not found", serviceId);
            }
        }

        newOrder.planned_cost = plannedCost;

        Order changedOrder = orderDao.changeOrder(newOrder.id, newOrder);

        List<OrderService> orderServiceList =
                orderDto.servicesIds.stream()
                        .map(serviceId -> orderServiceDao.assignService(newOrder.id, serviceId))
                        .collect(Collectors.toList());

        FullOrderDto fullOrderDto = new FullOrderDto();
        fullOrderDto.id = changedOrder.id;
        fullOrderDto.workDescription = changedOrder.work_description;
        fullOrderDto.status = changedOrder.status.toString();
        fullOrderDto.plannedCost = changedOrder.planned_cost;
        fullOrderDto.plannedEndDate =
                Utils.getStringLocalDateTimeFormat(changedOrder.planned_end_date);
        fullOrderDto.totalCost = changedOrder.total_cost;
        fullOrderDto.endDate = Utils.getStringLocalDateTimeFormat(changedOrder.end_date);

        fullOrderDto.servicesIds = orderServiceList.stream()
                .map(orderService -> orderService.serviceId)
                .collect(Collectors.toList());
        fullOrderDto.carId = changedOrder.car_id;
        fullOrderDto.stationId = changedOrder.station_id;
        fullOrderDto.orderDate = Utils.getStringLocalDateTimeFormat(changedOrder.order_date_time);

        return fullOrderDto;
    }

    @Override
    public OrderDto changeOrder(Long orderId, OrderDto newOrder) {
        return mapper.mapServerObjectToDto(orderDao.changeOrder(orderId, mapper.mapDtoToServerObject(newOrder)));
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderDao.deleteOrder(orderId);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return mapper.mapServerObjectToDto(orderDao.getOrderById(orderId));
    }

    @Override
    public List<OrderDto> getOrdersByStationId(Integer stationId, String timestamp) {
        return orderDao.getOrdersByStationAndDate(stationId, Utils.getLocalDate(timestamp)).stream()
                .map(order -> (OrderDto)mapper.mapServerObjectToDto(order))
                .collect(Collectors.toList());
    }
}
