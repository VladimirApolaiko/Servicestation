package org.servicestation.dao.impl;

import org.servicestation.dao.IMechanicDao;
import org.servicestation.dao.exceptions.NullPropertiesException;
import org.servicestation.model.Mechanic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(MechanicDaoImpl.class);

    private static final String DELIMITER = ", ";

    private static final String INSERT_MECHANIC =
            "insert into mechanic (station_id, firstname, lastname) values(:station_id, :firstname, :lastname);";

    private static final String UPDATE_MECHANIC = "update mechanic set ";

    private static final String SELECT_MECHANIC_BY_ID = "select * from mechanic where id=:id";

    private static final String DELETE_MECHANIC = "delete from mechanic where id=:id";

    private static final String SELECT_ALL_MECHANICS = "select * from mechanic where station_id = :station_id";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Mechanic createMechanic(final Mechanic mechanic) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", mechanic.station_id);
        params.addValue("firstname", mechanic.firstname);
        params.addValue("lastname", mechanic.lastname);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(INSERT_MECHANIC, params, keyHolder);

        return getMechanic(keyHolder.getKeys());
    }

    @Override
    public Mechanic changeMechanic(final Integer mechanicId, final Mechanic newMechanic) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder(UPDATE_MECHANIC);
        boolean notNull = false;

        for (Field field : newMechanic.getClass().getFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(newMechanic);

                if (field.get(newMechanic) != null) {
                    params.addValue(field.getName(), value);
                    sql.append(getColumnMapping(field.getName()));
                    notNull = true;
                }
            } catch (IllegalAccessException e) {
                LOGGER.debug("Can't get value of field " + field.getName(), e);
            }
        }

        if (!notNull) throw new NullPropertiesException("At least one property should be not null");

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

        return namedParameterJdbcTemplate.queryForObject(SELECT_MECHANIC_BY_ID, params, (rs, rowNum) -> {
            return getMechanic(rs);
        });
    }

    @Override
    public Mechanic deleteMechanicById(final Integer mechanicId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", mechanicId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(DELETE_MECHANIC, params, keyHolder);

        return getMechanic(keyHolder.getKeys());
    }

    @Override
    public List<Mechanic> getAllMechanics(final Integer stationId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("station_id", stationId);
        List<Mechanic> mechanics = new ArrayList<>();

        namedParameterJdbcTemplate.query(SELECT_ALL_MECHANICS, params, rs -> {
            mechanics.add(getMechanic(rs));
        });
        return mechanics;
    }

    private Mechanic getMechanic(Map<String, Object> keys) {
        if (keys == null) return null;

        Mechanic mechanic = new Mechanic();
        mechanic.id = (int) keys.get("id");
        mechanic.firstname = (String) keys.get("firstname");
        mechanic.lastname = (String) keys.get("lastname");
        mechanic.station_id = (int) keys.get("station_Id");
        return mechanic;
    }

    private Mechanic getMechanic(final ResultSet rs) throws SQLException {
        Mechanic mechanic = new Mechanic();
        mechanic.id = rs.getInt("id");
        mechanic.firstname = rs.getString("firstname");
        mechanic.lastname = rs.getString("lastname");
        mechanic.station_id = rs.getInt("station_id");

        return mechanic;
    }

    private String getColumnMapping(final String columnName) {
        return columnName + "=:" + columnName + DELIMITER;
    }


}
