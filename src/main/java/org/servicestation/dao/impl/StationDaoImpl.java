package org.servicestation.dao.impl;

import org.servicestation.dao.IStationDao;
import org.servicestation.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StationDaoImpl implements IStationDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Station createStation(String name, String address, String description, double latitude, double longitude) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        params.addValue("address", address);
        params.addValue("description", description);
        params.addValue("latitude", latitude);
        params.addValue("longitude", longitude);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                "INSERT INTO station (name, address, description, latitude, longitude) " +
                        "VALUES(:name, :address, :description, :latitude, :longitude)", params, keyHolder);

        return getStationInfoFromKeys(keyHolder.getKeys());
    }

    @Override
    public Station getStationById(int stationId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", stationId);

        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM station WHERE id=:id", params, (rs, rowNum) -> {
            return getStationInfoFromResultSet(rs);
        });
    }

    @Override
    public List<Station> getAllStations() {
        List<Station> stations = new ArrayList<>();
        namedParameterJdbcTemplate.query("select * from station", (rs, rowNum) -> {
            while (rs.next()) {
                stations.add(getStationInfoFromResultSet(rs));
            }
            return stations;
        });
        return stations;
    }

    @Override
    public Station changeStation(int stationId, Station newStation) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", stationId);
        StringBuffer sql = new StringBuffer("update station set ");
        final String delimiter = ", ";


        if (newStation.name != null) {
            sql.append("name=:name");
            params.addValue("name", newStation.name);
        }
        if (newStation.address != null) {
            sql.append("address=:address");
            sql.append(delimiter);
            params.addValue("address", newStation.address);
        }
        if (newStation.description != null) {
            sql.append("description=:description");
            sql.append(delimiter);
            params.addValue("description", newStation.description);
        }
        if (newStation.latitude != null) {
            sql.append("latitude=:latitude");
            sql.append(delimiter);
            params.addValue("latitude", newStation.latitude);
        }
        if (newStation.longitude != null) {
            sql.append("longitude=:longitude");
            params.addValue("longitude", newStation.longitude);
        }
        sql.append(" where id=:id");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql.toString(), params, keyHolder);
        return getStationInfoFromKeys(keyHolder.getKeys());
    }

    @Override
    public Station deleteStation(int stationId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", stationId);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update("delete from station where id=:id", params, keyHolder);

        return getStationInfoFromKeys(keyHolder.getKeys());
    }

    private Station getStationInfoFromKeys(Map<String, Object> keys) {
        Station updatedStation = new Station();
        updatedStation.id = (int) keys.get("id");
        updatedStation.name = (String) keys.get("name");
        updatedStation.address = (String) keys.get("address");
        updatedStation.description = (String) keys.get("description");
        updatedStation.latitude = (Double) keys.get("latitude");
        updatedStation.longitude = (Double) keys.get("longitude");

        return updatedStation;
    }

    private Station getStationInfoFromResultSet(ResultSet rs) throws SQLException {
        Station station = new Station();
        station.id = rs.getInt("id");
        station.name = rs.getString("name");
        station.address = rs.getString("address");
        station.description = rs.getString("description");
        station.latitude = rs.getDouble("latitude");
        station.longitude = rs.getDouble("longitude");
        return station;
    }
}
