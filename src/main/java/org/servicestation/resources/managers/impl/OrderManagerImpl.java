package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IOrderDao;
import org.servicestation.dao.IOrderServiceDao;
import org.servicestation.dao.IServiceDao;
import org.servicestation.model.Order;
import org.servicestation.model.OrderService;
import org.servicestation.model.Status;
import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.exceptions.OrderNotFoundException;
import org.servicestation.resources.managers.Authority;
import org.servicestation.resources.managers.IOrderManager;
import org.servicestation.resources.mappers.IObjectMapper;
import org.servicestation.resources.sokets.IWebSocketEventEmitter;
import org.servicestation.resources.sokets.WebSocketEvent;
import org.servicestation.resources.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.io.IOException;
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

    @Autowired
    private IWebSocketEventEmitter webSocketEventEmitter;

    @Override
    public FullOrderDto createNewOrder(String username, FullOrderDto orderDto) throws IOException {
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

        webSocketEventEmitter.emit(username, WebSocketEvent.ORDERS_CHANGED, null);
        webSocketEventEmitter.emitForAuthorities(Authority.ROLE_STATION_ADMIN, WebSocketEvent.ORDERS_CHANGED, null);
        return dto;
    }

    @Override
    public FullOrderDto getOrderById(String username, Long orderId) throws OrderNotFoundException {
        if(!isOrderExist(username, orderId)) {
            throw new OrderNotFoundException("Order with id " + orderId + " not found for user " + username);
        }

        Order order = orderDao.getOrderById(orderId);

        List<OrderService> services = orderServiceDao.getServicesByOrderId(order.id);

        FullOrderDto dto = mapper.mapServerObjectToDto(order);
        dto.servicesIds = getServiceIds(services);
        return dto;
    }

    @Override
    public FullOrderDto changeOrder(String username, Long orderId, FullOrderDto newOrder) throws OrderNotFoundException, IOException {

        if(!isOrderExist(username, orderId)){
            throw new OrderNotFoundException("Order with id " + orderId + " not found for user " + username);
        }

        Order order = orderDao.changeOrder(orderId, mapper.mapDtoToServerObject(newOrder));
        orderServiceDao.unassignServices(orderId);

        FullOrderDto dto = mapper.mapServerObjectToDto(order);

        dto.servicesIds = newOrder.servicesIds.stream()
                .map(serviceId -> orderServiceDao.assignService(orderId, serviceId))
                .map(orderService -> orderService.serviceId).collect(Collectors.toList());

        webSocketEventEmitter.emit(username, WebSocketEvent.ORDERS_CHANGED, null);
        webSocketEventEmitter.emitForAuthorities(Authority.ROLE_STATION_ADMIN, WebSocketEvent.ORDERS_CHANGED, null);
        return dto;
    }

    @Override
    public void deleteOrder(Long orderId) throws IOException {
        Order orderById = orderDao.getOrderById(orderId);
        orderServiceDao.unassignServices(orderId);
        orderDao.deleteOrder(orderId);

        webSocketEventEmitter.emit(orderById.username, WebSocketEvent.ORDERS_CHANGED, null);
        webSocketEventEmitter.emitForAuthorities(Authority.ROLE_STATION_ADMIN, WebSocketEvent.ORDERS_CHANGED, null);

    }

    @Override
    public List<FullOrderDto> getOrdersByStationId(Integer stationId, String startDateTimestamp, String endDateTimestamp) {
        return getDto(orderDao.getOrdersByStationId(stationId, Utils.getLocalDate(startDateTimestamp), Utils.getLocalDate(endDateTimestamp)));
    }

    @Override
    public List<FullOrderDto> getOrdersByStationId(Integer stationId) {
        return getDto(orderDao.getOrdersByStationId(stationId));
    }

    @Override
    public List<FullOrderDto> getOrdersByStationId(Integer stationId, String status) {
        return getDto(orderDao.getOrdersByStationId(stationId, Status.valueOf(status)));
    }

    @Override
    public List<FullOrderDto> getOrdersByUsername(String username, String startDateTimestamp, String endDateTimestamp) {
        return getDto(orderDao.getOrdersByUsername(username, Utils.getLocalDate(startDateTimestamp), Utils.getLocalDate(endDateTimestamp)));
    }

    @Override
    public List<FullOrderDto> getOrdersByUsername(String username) {
        return getDto(orderDao.getOrdersByUsername(username));
    }

    @Override
    public List<FullOrderDto> getOrdersByUsername(String username, String status) {
        return getDto(orderDao.getOrdersByUsername(username, Status.valueOf(status)));
    }

    private List<Integer> getServiceIds(List<OrderService> services) {
        return services.stream().map(orderService -> orderService.serviceId).collect(Collectors.toList());
    }

    private List<FullOrderDto> getDto(List<Order> orders) {
        return orders.stream().map(order -> {
            List<OrderService> services = orderServiceDao.getServicesByOrderId(order.id);

            FullOrderDto dto = mapper.mapServerObjectToDto(order);
            dto.servicesIds = getServiceIds(services);

            return dto;
        }).collect(Collectors.toList());
    }

    private boolean isOrderExist(String username, Long orderId) throws OrderNotFoundException {
        List<Long> orders;
        try{
            orders = orderDao.getOrdersByUsername(username).stream().map(order -> order.id).collect(Collectors.toList());
        }catch(EmptyResultDataAccessException e) {
            throw new OrderNotFoundException("Order with id " + orderId + " not found for user " + username);
        }

        return orders.contains(orderId);
    }
}
