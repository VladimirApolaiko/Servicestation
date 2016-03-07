package org.servicestation.dao.impl;

import org.servicestation.dao.IOrderDao;
import org.servicestation.dao.exceptions.NullProperiesException;
import org.servicestation.model.Order;
import org.servicestation.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements IOrderDao {

    private static final String DELIMITER = ", ";

    private static String CREATE_ORDER = "insert into \"order\" (status) values(cast(:status as order_status))";

    private static String UPDATE_ORDER = "update \"order\" set ";

    private static String DELETE_ORDER = "delete from \"order\" where id=:id";

    private static String SELECT_ORDER = "select * from \"order\" where id=:id";

    private static String SELECT_ORDERS_BY_STATION_ID = "select * from order where station_id=:station_id";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Order createNewOrder() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("status", Status.INIT.toString());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(CREATE_ORDER, params, keyHolder);

        return getOrder(keyHolder.getKeys());
    }

    @Override
    public Order changeOrder(final Integer orderId, final Order newOrder) throws Exception {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder(UPDATE_ORDER);
        boolean notNull = false;

        for(Field field : newOrder.getClass().getFields()) {
            field.setAccessible(true);
            Object value = field.get(newOrder);
            if(value != null) {
                params.addValue(field.getName(), value);
                sql.append(getColumnMapping(field.getName()));
                notNull = true;
            }
        }

        if (!notNull) throw new NullProperiesException("At least one property should be not null");

        sql.deleteCharAt(sql.length() - 2);//delete last delimiter
        params.addValue("id", orderId);
        sql.append("where id=:id");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql.toString(), params, keyHolder);

        return getOrder(keyHolder.getKeys());
    }

    @Override
    public Order deleteOrder(final Integer orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", orderId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(DELETE_ORDER, params, keyHolder);

        return getOrder(keyHolder.getKeys());
    }

    @Override
    public Order getOrderById(final Integer orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", orderId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SELECT_ORDER, params, keyHolder);

        return getOrder(keyHolder.getKeys());
    }

    @Override
    public List<Order> getAllOrders(final Integer stationId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);
        List<Order> orders = new ArrayList<>();

        namedParameterJdbcTemplate.query(SELECT_ORDERS_BY_STATION_ID, params, rs -> {
            orders.add(getOrder(rs));
        });
        return orders;
    }

    private Order getOrder(final Map<String, Object> keys){
        Order order = new Order();
        order.id = (Long)keys.get("id");
        order.initial_date = (Date)keys.get("initial_date");
        order.work_description = (String) keys.get("work_description");
        order.status = Status.valueOf((String)keys.get("status"));
        order.planned_cost = (Double) keys.get("planned_cost");
        order.planned_end_date = (Date) keys.get("planned_end_date");
        order.total_cost = (Double) keys.get("total_cost");
        order.end_date = (Date) keys.get("end_date");
        order.station_id = (Integer) keys.get("station_id");

        return order;
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
        order.station_id = rs.getInt("station_id");

        return order;
    }

    private String getColumnMapping(final String columnName) {
        return columnName + "=:" + columnName + DELIMITER;
    }
}
