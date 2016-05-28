package org.servicestation.dao.impl;

import org.servicestation.dao.IStationOrderDao;
import org.servicestation.model.StationOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StationOrderImpl implements IStationOrderDao {

    private static String ASSIGN_ORDER = "insert into order_station " +
            "(order_id, station_id, date_time) values(:order_id, :station_id, cast(:date_time as timestamp));";

    private static String UNASSIGN_ORDER = "delete from order_station where station_id=:station_id and order_id=:order_id";

    private static String GET_ALL_STATION_ORDERS = "select * from order_station where station_id=:station_id";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void assignOrder(final Integer stationId, final Long orderId, final String timestamp) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);
        params.addValue("order_id", orderId);
        params.addValue("date_time", timestamp);

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
    public List<StationOrder> getStationOrders(Integer stationId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);

        List<StationOrder> stationOrders = new ArrayList<>();

        namedParameterJdbcTemplate.query(GET_ALL_STATION_ORDERS, params, rs -> {
            stationOrders.add(getStationOrder(rs));
        });
        return stationOrders;
    }

    private StationOrder getStationOrder(ResultSet rs) throws SQLException {
        StationOrder stationOrder = new StationOrder();
        stationOrder.orderId = rs.getLong("station_id");
        stationOrder.stationId = rs.getInt("order_id");
        stationOrder.timestamp = rs.getObject("date_time").toString();

        return stationOrder;
    }
}
