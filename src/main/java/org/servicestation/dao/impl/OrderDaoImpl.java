package org.servicestation.dao.impl;

import org.servicestation.dao.IOrderDao;
import org.servicestation.dao.exceptions.NullPropertiesException;
import org.servicestation.model.Order;
import org.servicestation.model.Status;
import org.servicestation.resources.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements IOrderDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDaoImpl.class);

    private static final String DELIMITER = ", ";

    private static final String ALL_STATUSES = "ALL";

    private static String CREATE_ORDER = "insert into \"order\" " +
            "(status,username, station_id, order_date_time, car_id ) " +
            "values(cast(:status as order_status), :username, :station_id, cast(:order_date_time as timestamp), :car_id)";

    private static String UPDATE_ORDER = "update \"order\" set ";

    private static String DELETE_ORDER = "delete from \"order\" where id=:id";

    private static String SELECT_ORDER = "select * from \"order\" where id=:id";

    private static String GET_ALL_STATION_ORDERS_BY_DATE_RANGE = "select * from \"order\" " +
            "where station_id=:station_id and order_date_time >= cast(:order_date_time as timestamp) " +
            "and order_date_time <= cast(:next_date as timestamp)";

    private static String GET_ALL_ORDERS_BY_USERNAME_AND_DATE_RANGE = "select * from \"order\" " +
            "where username= :username and order_date_time >= cast(:order_date_time as timestamp) " +
            "and order_date_time <= cast(:next_date as timestamp)";

    private static String GET_ALL_ORDERS_BY_USERNAME_AND_STATUS = "select * from \"order\" where status = cast(:status as order_status)";

    private static String GET_ALL_ORDERS_BY_STATION_ID_AND_STATUS = "select * from \"order\" where status = cast(:status as order_status)";

    private static String GET_ALL_ORDERS_BY_USERNAME = "select * from \"order\" where username = :username";

    private static String GET_ALL_ORDERS_BY_STATION_ID = "select * from \"order\" where station_id = :station_id";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Order createNewOrder(Status status, String username, Integer stationId, LocalDateTime orderDateTime, Integer carId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("status", Status.INIT.toString());
        params.addValue("username", username);
        params.addValue("station_id", stationId);
        params.addValue("order_date_time", Utils.getStringLocalDateTimeFormat(orderDateTime));
        params.addValue("car_id", carId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(CREATE_ORDER, params, keyHolder);

        return getOrder(keyHolder.getKeys());
    }

    @Override
    public Order changeOrder(final Long orderId, final Order newOrder) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder(UPDATE_ORDER);
        boolean notNull = false;

        for (Field field : newOrder.getClass().getFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(newOrder);
                if (value != null) {
                    switch (field.getName()) {
                        case "status": {
                            params.addValue(field.getName(), value.toString());
                            sql.append("status = cast(:status as order_status)" + DELIMITER);
                        }
                        break;

                        case "planned_end_date": {
                            getDateFieldToQuery(params, sql, field, Utils.getStringLocalDateFormat((LocalDate) value));
                        }
                        break;

                        case "end_date": {
                            getDateFieldToQuery(params, sql, field, Utils.getStringLocalDateFormat((LocalDate) value));
                        }
                        break;

                        case "order_date_time": {
                            getDateFieldToQuery(params, sql, field, Utils.getStringLocalDateTimeFormat((LocalDateTime) value));
                        }
                        break;

                        default: {
                            addFieldToQuery(params, sql, field, value);
                        }
                    }
                    notNull = true;
                }
            } catch (IllegalAccessException e) {
                LOGGER.debug("Can't get value of field " + field.getName(), e);
            }
        }

        if (!notNull) throw new NullPropertiesException("At least one property should be not null");

        sql.deleteCharAt(sql.length() - 2);//delete last delimiter
        params.addValue("id", orderId);
        sql.append("where id=:id");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql.toString(), params, keyHolder);

        return getOrder(keyHolder.getKeys());
    }

    @Override
    public Order deleteOrder(final Long orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", orderId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(DELETE_ORDER, params, keyHolder);

        return getOrder(keyHolder.getKeys());
    }

    @Override
    public Order getOrderById(final Long orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", orderId);

        return namedParameterJdbcTemplate.queryForObject(SELECT_ORDER, params, (rs, rowNum) -> {
            return getOrder(rs);
        });
    }

    public List<Order> getOrdersByStationId(Integer stationId, LocalDate startDateTimestamp, LocalDate endDateTimestamp) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);
        params.addValue("order_date_time", Utils.getStringLocalDateFormat(startDateTimestamp));
        params.addValue("next_date", Utils.getStringLocalDateFormat(endDateTimestamp.plusDays(1)));

        List<Order> stationOrders = new ArrayList<>();

        namedParameterJdbcTemplate.query(GET_ALL_STATION_ORDERS_BY_DATE_RANGE, params, rs -> {
            stationOrders.add(getOrder(rs));
        });

        return stationOrders;
    }

    public List<Order> getOrdersByUsername(String username, LocalDate startDateTimestamp, LocalDate endDateTimestamp) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        params.addValue("order_date_time", Utils.getStringLocalDateFormat(startDateTimestamp));
        params.addValue("next_date", Utils.getStringLocalDateFormat(endDateTimestamp.plusDays(1)));

        List<Order> orders = new ArrayList<>();

        namedParameterJdbcTemplate.query(GET_ALL_ORDERS_BY_USERNAME_AND_DATE_RANGE, params, rs -> {
            orders.add(getOrder(rs));
        });

        return orders;
    }

    @Override
    public List<Order> getOrdersByUsername(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        List<Order> orders = new ArrayList<>();

        namedParameterJdbcTemplate.query(GET_ALL_ORDERS_BY_USERNAME, params, rs -> {
            orders.add(getOrder(rs));
        });

        return orders;
    }

    @Override
    public List<Order> getOrdersByUsername(String username, Status status) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        params.addValue("status", status.toString());

        List<Order> orders = new ArrayList<>();

        namedParameterJdbcTemplate.query(GET_ALL_ORDERS_BY_USERNAME_AND_STATUS, params, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                orders.add(getOrder(rs));
            }
        });

        return orders;
    }

    @Override
    public List<Order> getOrdersByStationId(Integer stationId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);

        List<Order> orders = new ArrayList<>();
        namedParameterJdbcTemplate.query(GET_ALL_ORDERS_BY_STATION_ID, params, rs -> {
            orders.add(getOrder(rs));
        });

        return orders;
    }

    @Override
    public List<Order> getOrdersByStationId(Integer stationId, Status status) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);
        params.addValue("status", status.toString());

        List<Order> orders = new ArrayList<>();

        namedParameterJdbcTemplate.query(GET_ALL_ORDERS_BY_STATION_ID_AND_STATUS, params, rs -> {
            orders.add(getOrder(rs));
        });

        return orders;
    }

    private Order getOrder(final Map<String, Object> keys) {
        if (keys == null) return null;
        Order order = new Order();
        order.id = (Long) keys.get("id");
        order.work_description = (String) keys.get("work_description");
        order.status = Status.valueOf((String) keys.get("status"));
        order.planned_cost = (Double) keys.get("planned_cost");
        Timestamp plannedEndDate = ((Timestamp) keys.get("planned_end_date"));
        if (plannedEndDate != null) {
            order.planned_end_date = plannedEndDate.toLocalDateTime().toLocalDate();
        }
        order.total_cost = (Double) keys.get("total_cost");
        Timestamp endDate = ((Timestamp) keys.get("end_date"));
        if (endDate != null) {
            order.end_date = endDate.toLocalDateTime().toLocalDate();
        }
        order.username = (String) keys.get("username");
        order.station_id = (Integer) keys.get("station_id");
        order.order_date_time = ((Timestamp) keys.get("order_date_time")).toLocalDateTime();
        order.car_id = (Integer) keys.get("car_id");

        return order;
    }

    private Order getOrder(final ResultSet rs) throws SQLException {
        Order order = new Order();
        order.id = rs.getLong("id");
        order.work_description = rs.getString("work_description");
        order.status = Status.valueOf(rs.getString("status"));
        order.planned_cost = rs.getDouble("planned_cost");
        Timestamp plannedEndDate = ((Timestamp) rs.getObject("planned_end_date"));
        if (plannedEndDate != null) {
            order.planned_end_date = plannedEndDate.toLocalDateTime().toLocalDate();
        }
        order.total_cost = rs.getDouble("total_cost");
        Timestamp endDate = ((Timestamp) rs.getObject("end_date"));
        if (plannedEndDate != null) {
            order.planned_end_date = endDate.toLocalDateTime().toLocalDate();
        }

        order.username = rs.getString("username");
        order.station_id = rs.getInt("station_id");
        order.order_date_time = ((Timestamp) rs.getObject("order_date_time")).toLocalDateTime();
        order.car_id = rs.getInt("car_id");

        return order;
    }

    private String getColumnMapping(final String columnName) {
        return columnName + "=:" + columnName + DELIMITER;
    }

    private <T> void addFieldToQuery(MapSqlParameterSource params, StringBuilder sql, Field field, T value) {
        params.addValue(field.getName(), value);
        sql.append(getColumnMapping(field.getName()));
    }

    private <T> void getDateFieldToQuery(MapSqlParameterSource params, StringBuilder sql, Field field, T value) {
        params.addValue(field.getName(), value);
        sql.append(field.getName()).append(" = cast( :").append(field.getName()).append(" as timestamp").append(")").append(DELIMITER);
    }

}
