package org.servicestation.dao.impl;

import org.servicestation.dao.IStationOrderDao;
import org.servicestation.model.StationOrder;
import org.servicestation.resources.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StationOrderDaoImpl implements IStationOrderDao {

    private static String ASSIGN_ORDER = "insert into order_station " +
            "(order_id, station_id, date_time, car_id) values(:order_id, :station_id, cast(:date_time as timestamp), :car_id);";

    private static String UNASSIGN_ORDER = "delete from order_station where station_id=:station_id and order_id=:order_id";

    private static String GET_ALL_STATION_ORDERS = "select * from order_station " +
            "where station_id=:station_id and date_time >= cast(:date_time as timestamp) and date_time < cast(:next_date as timestamp)";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void assignOrder(final Integer stationId, final Long orderId, final LocalDateTime timestamp, final Integer carId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);
        params.addValue("order_id", orderId);
        params.addValue("date_time", Utils.getStringLocalDateTimeFormat(timestamp));
        params.addValue("car_id", carId);

        namedParameterJdbcTemplate.update(ASSIGN_ORDER, params);
    }

    @Override
    public void unAssignOrder(final Integer stationId, final Long orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);
        params.addValue("order_id", orderId);

        namedParameterJdbcTemplate.update(UNASSIGN_ORDER, params);
    }

    @Override
    public List<StationOrder> getStationOrders(Integer stationId, LocalDate localDate) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);
        params.addValue("date_time", Utils.getStringLocalDateFormat(localDate));
        params.addValue("next_date", Utils.getStringLocalDateFormat(localDate.plusDays(1)));

        List<StationOrder> stationOrders = new ArrayList<>();

        namedParameterJdbcTemplate.query(GET_ALL_STATION_ORDERS, params, rs -> {
            stationOrders.add(getStationOrder(rs));
        });
        return stationOrders;
    }

    private StationOrder getStationOrder(ResultSet rs) throws SQLException {
        StationOrder stationOrder = new StationOrder();
        stationOrder.id = rs.getLong("id");
        stationOrder.orderId = rs.getLong("station_id");
        stationOrder.stationId = rs.getInt("order_id");
        stationOrder.localDateTime = ((Timestamp)rs.getObject("date_time")).toLocalDateTime();

        return stationOrder;
    }
}
