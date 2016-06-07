package org.servicestation.dao.impl;

import org.servicestation.dao.IOrderServiceDao;
import org.servicestation.model.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderServiceDaoImpl implements IOrderServiceDao {

    private static final String ASSIGN_SERVICE_TO_ORDER =
            "insert into order_service (order_id, service_id) values(:order_id, :service_id)";

    private static final String GET_SERVICES_BY_ORDER_ID =
            "select * from order_service where order_id = :order_id";

    private static final String UNASSIGN_SERVICE =
            "delete from order_service where order_id = :order_id";


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public OrderService assignService(Long orderId, Integer serviceId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("order_id", orderId);
        params.addValue("service_id", serviceId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(ASSIGN_SERVICE_TO_ORDER, params, keyHolder);
        return getOrderService(keyHolder.getKeys());
    }

    @Override
    public void unassignService(Long orderId, Integer serviceId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("order_id", orderId);
        params.addValue("service_id", serviceId);

        jdbcTemplate.update(UNASSIGN_SERVICE, params);
    }

    @Override
    public List<OrderService> getServicesByOrderId(Long orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("order_id", orderId);

        List<OrderService> orderServices = new ArrayList<>();

        jdbcTemplate.query(GET_SERVICES_BY_ORDER_ID, params, rs -> {
            orderServices.add(getOrderService(rs));
        });

        return orderServices;

    }

    private OrderService getOrderService(Map<String, Object> keys) {
        OrderService orderService = new OrderService();
        orderService.id = (Long) keys.get("id");
        orderService.orderId = (Long) keys.get("order_id");
        orderService.serviceId = (Integer) keys.get("service_id");

        return orderService;
    }

    private OrderService getOrderService(ResultSet rs) throws SQLException {
        OrderService orderService = new OrderService();
        orderService.id = rs.getLong("id");
        orderService.orderId = rs.getLong("order_id");
        orderService.serviceId = rs.getInt("service_id");

        return orderService;
    }
}
