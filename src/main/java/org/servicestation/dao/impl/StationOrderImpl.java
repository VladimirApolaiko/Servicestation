package org.servicestation.dao.impl;

import org.servicestation.dao.IStationOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class StationOrderImpl implements IStationOrderDao {

    private static String ASSIGN_ORDER = "insert into order_station " +
            "(order_id, station_id) values(:order_id, :station_id);";

    private static String UNASSIGN_ORDER = "delete from order_station where station_id=:station_id and order_id=:order_id";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void assignOrder(final Integer stationId, final Long orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);
        params.addValue("order_id", orderId);

        namedParameterJdbcTemplate.update(ASSIGN_ORDER, params);
    }

    @Override
    public void unAssignOrder(Integer stationId, Long orderId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);
        params.addValue("order_id", orderId);

        namedParameterJdbcTemplate.update(UNASSIGN_ORDER, params);
    }
}
