package org.servicestation.dao.impl;

import org.servicestation.dao.DaoUtil;
import org.servicestation.dao.IOrderDao;
import org.servicestation.dao.exceptions.NullPropertiesException;
import org.servicestation.model.Order;
import org.servicestation.model.Status;
import org.servicestation.resources.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements IOrderDao {

    private static final String DELIMITER = ", ";

    private static String CREATE_ORDER = "insert into \"order\" (status) values(cast(:status as order_status))";

    private static String UPDATE_ORDER = "update \"order\" set ";

    private static String DELETE_ORDER = "delete from \"order\" where id=:id";

    private static String SELECT_ORDER = "select * from \"order\" where id=:id";

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
    public Order changeOrder(final Long orderId, final Order newOrder) throws Exception {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder(UPDATE_ORDER);
        boolean notNull = false;

        for(Field field : newOrder.getClass().getFields()) {
            field.setAccessible(true);
            Object value = field.get(newOrder);
            if(value != null) {
                switch (field.getName()) {
                    case "status": {
                        params.addValue(field.getName(), value.toString());
                        sql.append("status = cast(:status as order_status)" + DELIMITER);
                    }
                    break;

                    case "planned_end_date": {
                        getDateFieldToQuery(params, sql, field, Utils.getStringLocalDateTimeFormat((LocalDateTime)value));
                    }
                    break;

                    case "end_date": {
                        getDateFieldToQuery(params, sql, field, Utils.getStringLocalDateTimeFormat((LocalDateTime)value));
                    }
                    break;

                    default: {
                        addFieldToQuery(params, sql, field, value);
                    }
                }
                notNull = true;
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

    private Order getOrder(final Map<String, Object> keys){
        if(keys == null) return null;
        Order order = new Order();
        order.id = (Long)keys.get("id");
        order.work_description = (String) keys.get("work_description");
        order.status = Status.valueOf((String)keys.get("status"));
        order.planned_cost = (Double) keys.get("planned_cost");
        Timestamp plannedEndDate = ((Timestamp)keys.get("planned_end_date"));
        if(plannedEndDate != null) {
            order.planned_end_date = plannedEndDate.toLocalDateTime();
        }
        order.total_cost = (Double) keys.get("total_cost");
        Timestamp endDate = ((Timestamp)keys.get("end_date"));
        if(endDate != null) {
            order.end_date = endDate.toLocalDateTime();
        }

        return order;
    }

    private Order getOrder(final ResultSet rs) throws SQLException {
        Order order = new Order();
        order.id = rs.getLong("id");
        order.work_description = rs.getString("work_description");
        order.status = Status.valueOf(rs.getString("status"));
        order.planned_cost = rs.getDouble("planned_cost");
        Timestamp plannedEndDate = ((Timestamp)rs.getObject("planned_end_date"));
        if(plannedEndDate != null) {
            order.planned_end_date = plannedEndDate.toLocalDateTime();
        }
        order.total_cost = rs.getDouble("total_cost");
        Timestamp endDate = ((Timestamp)rs.getObject("end_date"));
        if(plannedEndDate != null) {
            order.planned_end_date = endDate.toLocalDateTime();
        }
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
