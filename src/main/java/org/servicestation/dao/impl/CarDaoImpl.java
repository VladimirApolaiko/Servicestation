package org.servicestation.dao.impl;

import org.servicestation.dao.ICarDao;
import org.servicestation.dao.exceptions.NullPropertiesException;
import org.servicestation.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
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

public class CarDaoImpl implements ICarDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarDaoImpl.class);

    private static final String DELIMITER = ", ";

    private static final String CREATE_NEW_CAR = "insert into car (brand, model, engine_volume, vin, registration_number, username)" +
            "values(:brand, :model, :engine_volume, :vin, :registration_number, :username)";

    private static final String GET_CAR_BY_USERNAME = "select * from car where username = :username";

    private static final String UPDATE_CAR = "update car set ";

    private static final String DELETE_CAR = "delete from car where id=:id";

    private static final String GET_CAR_BY_ID = "select * from car where id = :id";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Car createCar(String username, Car newCar) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("brand", newCar.brand);
        params.addValue("model", newCar.model);
        params.addValue("engine_volume", newCar.engine_volume);
        params.addValue("vin", newCar.vin);
        params.addValue("registration_number", newCar.registration_number);
        params.addValue("username", username);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(CREATE_NEW_CAR, params, keyHolder);

        return getCar(keyHolder.getKeys());

    }

    @Override
    public Car updateCar(int carId, Car newCar) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder(UPDATE_CAR);
        boolean notNull = false;
        for (Field field : newCar.getClass().getFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(newCar);

                if (field.get(newCar) != null) {
                    params.addValue(field.getName(), value);
                    sql.append(getColumnMapping(field.getName()));
                    notNull = true;
                }
            } catch (IllegalAccessException e) {
                LOGGER.debug("Can't get value of field " + field.getName(), e);
            }
        }

        if (!notNull) throw new NullPropertiesException("At least one property should be not null");

        sql.deleteCharAt(sql.length() - 2);
        params.addValue("id", carId);
        sql.append("where id=:id");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql.toString(), params, keyHolder);

        return getCar(keyHolder.getKeys());
    }

    @Override
    public Car deleteCar(int carId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", carId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(DELETE_CAR, params, keyHolder);

        return getCar(keyHolder.getKeys());
    }

    @Override
    public List<Car> getCarsByUsername(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        List<Car> cars = new ArrayList<>();

        namedParameterJdbcTemplate.query(GET_CAR_BY_USERNAME, params, rs -> {
            cars.add(getCar(rs));
        });

        return cars;
    }

    @Override
    public Car getCarById(Integer carId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", carId);

        return namedParameterJdbcTemplate.queryForObject(GET_CAR_BY_ID, params, (rs, rowNum) -> {
            return getCar(rs);
        });
    }

    private Car getCar(Map<String, Object> keys) {
        Car car = new Car();

        car.id = (int) keys.get("id");
        car.brand = (String) keys.get("brand");
        car.model = (String) keys.get("model");
        car.vin = (String) keys.get("vin");
        car.engine_volume = (double) keys.get("engine_volume");
        car.registration_number = (String) keys.get("registration_number");
        car.username = (String) keys.get("username");

        return car;
    }

    private Car getCar(ResultSet rs) throws SQLException {
        Car car = new Car();

        car.id = rs.getInt("id");
        car.brand = rs.getString("brand");
        car.model = rs.getString("model");
        car.vin = rs.getString("vin");
        car.engine_volume = rs.getDouble("engine_volume");
        car.registration_number = rs.getString("registration_number");
        car.username = rs.getString("username");

        return car;
    }

    private String getColumnMapping(final String columnName) {
        return columnName + "=:" + columnName + DELIMITER;
    }
}
