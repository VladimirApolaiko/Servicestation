package org.servicestation.dao.impl;

import org.servicestation.dao.IAdminStationDao;
import org.servicestation.model.AdminStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.ws.rs.GET;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminStationDaoImpl implements IAdminStationDao {

    private static final String ASSIGN_ADMIN = "insert into admin_station values(:username, :station_id)" ;

    private static final String UNASSIGN_ADMIN = "delete from admin_station where username = :username";

    private static final String GET_ADMIN_STATION = "select * from admin_station where username = :username";

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
    public void unassignAdmin(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        namedParameterJdbcTemplate.update(UNASSIGN_ADMIN, params);
    }

    @Override
    public AdminStation getAdminStationByUsername(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        return namedParameterJdbcTemplate.queryForObject(GET_ADMIN_STATION, params, new RowMapper<AdminStation>() {
            @Override
            public AdminStation mapRow(ResultSet rs, int rowNum) throws SQLException {
                return getAdminStation(rs);
            }
        });
    }


    private AdminStation getAdminStation(ResultSet rs) throws SQLException {
        AdminStation adminStation = new AdminStation();

        adminStation.stationId = rs.getInt("station_id");
        adminStation.username = rs.getString("username");

        return adminStation;
    }

}
