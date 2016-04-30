package org.servicestation.dao.impl;

import org.servicestation.dao.IStationDao;
import org.servicestation.dao.exceptions.NullPropertiesException;
import org.servicestation.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StationDaoImpl implements IStationDao {

    private static final String DELIMITER = ", ";

    private static final String CREATE_STATION = "INSERT INTO station (station_name, address, description, latitude, longitude) " +
            "VALUES(:station_name, :address, :description, :latitude, :longitude)";

    private static final String SELECT_STATION = "SELECT * FROM station WHERE id=:id";

    private static final String SELECT_ALL_STATIONS = "select * from station";

    private static final String UPDATE_STATION = "update station set ";

    private static final String DELETE_STATION = "delete from station where id=:id";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Station createStation(final Station newStation) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_name", newStation.station_name);
        params.addValue("address", newStation.address);
        params.addValue("description", newStation.description);
        params.addValue("latitude", newStation.latitude);
        params.addValue("longitude", newStation.longitude);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(CREATE_STATION, params, keyHolder);

        return getStationInfoFromKeys(keyHolder.getKeys());
    }

    @Override
    public Station getStationById(final Integer stationId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", stationId);

        return namedParameterJdbcTemplate.queryForObject(SELECT_STATION, params, (rs, rowNum) -> {
            return getStationInfoFromResultSet(rs);
        });
    }

    @Override
    public List<Station> getAllStations() {
        List<Station> stations = new ArrayList<>();
        namedParameterJdbcTemplate.query(SELECT_ALL_STATIONS, (rs, rowNum) -> {
            stations.add(getStationInfoFromResultSet(rs));
            return stations;
        });
        return stations;
    }

    @Override
    public Station changeStation(final Integer stationId, final Station newStation) throws Exception {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder(UPDATE_STATION);
        boolean notNull = false;

        for (Field field : newStation.getClass().getFields()) {
            field.setAccessible(true);
            Object value = field.get(newStation);

            if (field.get(newStation) != null) {
                params.addValue(field.getName(), value);
                sql.append(getColumnMapping(field.getName()));
                notNull = true;
            }
        }

        if(!notNull) throw new NullPropertiesException("At least one property should be not null");

        sql.deleteCharAt(sql.length() - 2);
        params.addValue("id", stationId);
        sql.append("where id=:id");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql.toString(), params, keyHolder);

        return getStationInfoFromKeys(keyHolder.getKeys());
    }

    @Override
    public Station deleteStation(final Integer stationId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", stationId);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(DELETE_STATION, params, keyHolder);

        return getStationInfoFromKeys(keyHolder.getKeys());
    }

    private Station getStationInfoFromKeys(final Map<String, Object> keys) {
        if(keys == null) return null;
        Station updatedStation = new Station();
        updatedStation.id = (int) keys.get("id");
        updatedStation.station_name = (String) keys.get("station_name");
        updatedStation.address = (String) keys.get("address");
        updatedStation.description = (String) keys.get("description");
        updatedStation.latitude = (Double) keys.get("latitude");
        updatedStation.longitude = (Double) keys.get("longitude");

        return updatedStation;
    }

    private Station getStationInfoFromResultSet(final ResultSet rs) throws SQLException {
        Station station = new Station();
        station.id = rs.getInt("id");
        station.station_name = rs.getString("station_name");
        station.address = rs.getString("address");
        station.description = rs.getString("description");
        station.latitude = rs.getDouble("latitude");
        station.longitude = rs.getDouble("longitude");
        return station;
    }

    private String getColumnMapping(final String columnName) {
        return columnName + "=:" + columnName + DELIMITER;
    }
}
