package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IOrderDao;
import org.servicestation.dao.IOrderServiceDao;
import org.servicestation.dao.IServiceDao;
import org.servicestation.model.Order;
import org.servicestation.model.OrderService;
import org.servicestation.model.Status;
import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.managers.IOrderManager;
import org.servicestation.resources.mappers.IObjectMapper;
import org.servicestation.resources.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
            if (servicePrices.get(serviceId) != null) {
                plannedCost += servicePrices.get(serviceId);
            } else {
                LOGGER.info("Service with service id {} not found", serviceId);
            }
        }

        newOrder.planned_cost = plannedCost;

        Order changedOrder = orderDao.changeOrder(newOrder.id, newOrder);

        List<OrderService> orderServiceList =
                orderDto.servicesIds.stream()
                        .map(serviceId -> orderServiceDao.assignService(newOrder.id, serviceId))
                        .collect(Collectors.toList());

        FullOrderDto dto = mapper.mapServerObjectToDto(changedOrder);
        dto.servicesIds = getServiceIds(orderServiceList);

        return dto;
    }

    @Override
    public FullOrderDto getOrderById(Long orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderService> services = orderServiceDao.getServicesByOrderId(order.id);

        FullOrderDto dto = mapper.mapServerObjectToDto(order);
        dto.servicesIds = getServiceIds(services);
        return dto;
    }

    @Override
    public FullOrderDto changeOrder(Long orderId, FullOrderDto newOrder) {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderServiceDao.unassignServices(orderId);
        orderDao.deleteOrder(orderId);
    }

    @Override
    public List<FullOrderDto> getOrdersByStationId(Integer stationId, String timestamp) {
        List<Order> orders = orderDao.getOrdersByStationAndDate(stationId, Utils.getLocalDate(timestamp));

        return orders.stream().map(order -> {
            List<OrderService> services = orderServiceDao.getServicesByOrderId(order.id);

            FullOrderDto dto = mapper.mapServerObjectToDto(order);
            dto.servicesIds = getServiceIds(services);

            return dto;
        }).collect(Collectors.toList());
    }

    private List<Integer> getServiceIds(List<OrderService> services) {
        return services.stream().map(orderService -> orderService.serviceId).collect(Collectors.toList());
    }
}
