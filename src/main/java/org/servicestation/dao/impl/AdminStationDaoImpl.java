package org.servicestation.dao.impl;

import org.servicestation.dao.IAdminStationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class AdminStationDaoImpl implements IAdminStationDao {

    private static final String ASSIGN_ADMIN = "insert into admin_station values(:username, :station_id)" ;

    private static final String UNASSIGN_ADMIN = "delete from admin_station where username = :username";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void assignAdmin(String username, Integer stationId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        params.addValue("station_id", stationId);

        namedParameterJdbcTemplate.update(ASSIGN_ADMIN, params);
    }

    @Override
    public void unassignAdmin(String username, Integer stationId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        namedParameterJdbcTemplate.update(UNASSIGN_ADMIN, params);
    }
}
