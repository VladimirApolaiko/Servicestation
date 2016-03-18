package org.servicestation.dao.impl;

import org.servicestation.dao.IMechanicOrder;
import org.servicestation.model.Order;
import org.servicestation.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MechanicOrderImpl implements IMechanicOrder {

    private static String ASSIGN_ORDER = "insert into mechanic_order " +
            "(order_id, mechanic_id) values(:order_id, :mechanic_id);";

    private static String GET_MECHANIC_ORDERS = "select * from \"order\" where id in " +
            "(select order_id from mechanic_order where mechanic_id=:mechanic_id)";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void assignOrder(final Integer mechanicId, final Long orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mechanic_id", mechanicId);
        params.addValue("order_id", orderId);

        namedParameterJdbcTemplate.update(ASSIGN_ORDER, params);
    }

    @Override
    public List<Order> getMechanicOrders(final Integer mechanicId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mechanic_id", mechanicId);
        List<Order> orders = new ArrayList<>();
        namedParameterJdbcTemplate.query(GET_MECHANIC_ORDERS, params, rs -> {
            orders.add(getOrder(rs));
        });

        return orders;
    }

    private Order getOrder(final ResultSet rs) throws SQLException {
        Order order = new Order();
        order.id = rs.getLong("id");
        order.initial_date = rs.getDate("initial_date");
        order.work_description = rs.getString("work_description");
        order.status = Status.valueOf(rs.getString("status"));
        order.planned_cost = rs.getDouble("planned_cost");
        order.planned_end_date = rs.getDate("planned_end_date");
        order.total_cost = rs.getDouble("total_cost");
        order.end_date = rs.getDate("end_date");
        order.station_id = rs.getLong("station_id");

        return order;
    }
}
