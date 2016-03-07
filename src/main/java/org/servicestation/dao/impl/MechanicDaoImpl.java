package org.servicestation.dao.impl;

import org.servicestation.dao.IMechanicDao;
import org.servicestation.dao.exceptions.NullProperiesException;
import org.servicestation.model.Mechanic;
import org.servicestation.model.Profile;
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

public class MechanicDaoImpl implements IMechanicDao {

    private static final String DELIMITER = ", ";

    private static final String INSERT_MECHANIC = "insert into mechanic (nickname, station_id) " +
            "values(:nickname, :station_id);";

    private static final String UPDATE_MECHANIC = "update mechanic set ";

    private static final String SELECT_MECHANIC = "select * from mechanic where id=:id";

    private static final String DELETE_MECHANIC = "delete from mechanic where id=:id";

    private static final String SELECT_ALL_MECHANICS = "select * from mechanic";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Mechanic createMechanic(final String nickname, final Integer stationId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nickname", nickname);
        params.addValue("station_id", stationId);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(INSERT_MECHANIC,
                params,
                keyHolder,
                new String[]{"id", "nickname", "station_id"});

        return getMechanic(keyHolder.getKeys());
    }



    public Mechanic changeMechanic(final Integer mechanicId, final Mechanic newMechanic) throws Exception {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder(UPDATE_MECHANIC);
        boolean notNull = false;

        for(Field field : newMechanic.getClass().getFields()) {
            field.setAccessible(true);
            Object value = field.get(newMechanic);
            if(value != null) {
                params.addValue(field.getName(), value);
                sql.append(getColumnMapping(field.getName()));
                notNull = true;
            }
        }

        if (!notNull) throw new NullProperiesException("At least one property should be not null");

        sql.deleteCharAt(sql.length() - 2);//delete last delimiter
        params.addValue("id", mechanicId);
        sql.append("where id=:id");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql.toString(), params, keyHolder);

        return getMechanic(keyHolder.getKeys());
    }

    @Override
    public Mechanic getMechanicById(final Integer mechanicId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", mechanicId);

        return namedParameterJdbcTemplate.queryForObject(SELECT_MECHANIC, params, (rs, rowNum) -> {
            return getMechanic(rs);
        });
    }

    @Override
    public Mechanic deleteMechanic(final Integer mechanicId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", mechanicId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(DELETE_MECHANIC, params, keyHolder);

        return getMechanic(keyHolder.getKeys());
    }

    @Override
    public List<Mechanic> getAllMechanics(final Integer stationId) {
        List<Mechanic> mechanics = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);

        namedParameterJdbcTemplate.query(SELECT_ALL_MECHANICS, params, rs -> {
            mechanics.add(getMechanic(rs));
        });
        return mechanics;
    }

    private Mechanic getMechanic(Map<String, Object> keys) {
        if (keys == null) return null;

        Mechanic mechanic = new Mechanic();
        mechanic.id = (int) keys.get("id");
        mechanic.nickname = (String) keys.get("nickname");
        mechanic.stationId = (int) keys.get("station_Id");
        return mechanic;
    }

    private Mechanic getMechanic(final ResultSet rs) throws SQLException {
        Mechanic mechanic = new Mechanic();
        mechanic.id = rs.getInt("id");
        mechanic.nickname = rs.getString("nickname");
        mechanic.stationId = rs.getInt("station_id");

        return mechanic;
    }

    private String getColumnMapping(final String columnName) {
        return columnName + "=:" + columnName + DELIMITER;
    }


}
