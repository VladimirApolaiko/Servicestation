package org.servicestation.dao.impl;

import org.servicestation.dao.IUserDao;
import org.servicestation.dao.exceptions.NullPropertiesException;
import org.servicestation.model.User;
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
import java.util.Map;

public class UserDaoImpl implements IUserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final String DELIMITER = ", ";

    private static final String CREATE_NEW_USER =
            "insert into users values(:username, :password, :enabled, :firstname, :lastname)";

    private static final String UPDATE_USER =
            "update users set ";

    private static final String DELETE_USER =
            "delete from users where username = :username";

    private static final String GET_USER =
            "select * from users where username = :username";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User createUser(final User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", user.username);
        params.addValue("password", user.password);
        params.addValue("enabled", user.enabled);
        params.addValue("firstname", user.firstName);
        params.addValue("lastname", user.lastName);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(CREATE_NEW_USER, params, keyHolder);

        return getUser(keyHolder.getKeys());

    }

    public User changeUserByUsername(final String username, final User newUser) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder(UPDATE_USER);
        boolean notNull = false;

        for (Field field : newUser.getClass().getFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(newUser);
            } catch (IllegalAccessException e) {
                LOGGER.debug("Can't get value of field " + field.getName(), e);
            }
            if (value != null) {
                params.addValue(field.getName(), value);
                sql.append(getColumnMapping(field.getName()));
            }
            notNull = true;
        }

        if (!notNull) throw new NullPropertiesException("At least one property should be not null");

        sql.deleteCharAt(sql.length() - 2);//delete last delimiter
        params.addValue("username", username);
        sql.append("where username=:username");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql.toString(), params, keyHolder);

        return getUser(keyHolder.getKeys());
    }


    @Override
    public User deleteUserByUsername(final String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(DELETE_USER, params, keyHolder);

        return getUser(keyHolder.getKeys());
    }

    @Override
    public User getUserByUsername(final String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        return namedParameterJdbcTemplate.queryForObject(GET_USER, params, (rs, rowNum) -> {
            return getUser(rs);
        });
    }

    private User getUser(Map<String, Object> keys) {
        User user = new User();
        user.username = (String) keys.get("username");
        user.password = (String) keys.get("password");
        user.enabled = (boolean) keys.get("enabled");
        user.firstName = (String) keys.get("firstname");
        user.lastName = (String) keys.get("lastname");

        return user;
    }

    private User getUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.username = rs.getString("username");
        user.password = rs.getString("password");
        user.enabled = rs.getBoolean("enabled");
        user.firstName = rs.getString("firstname");
        user.lastName = rs.getString("lastname");

        return user;
    }

    private String getColumnMapping(final String columnName) {
        return columnName + "=:" + columnName + DELIMITER;
    }
}
