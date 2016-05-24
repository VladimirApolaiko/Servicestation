package org.servicestation.dao.impl;

import org.servicestation.dao.IPasswordRecoveryDao;
import org.servicestation.model.PasswordRecovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.ws.rs.GET;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public class PasswordDaoImpl implements IPasswordRecoveryDao {

    private static final String CREATE_PASSWORD_RECOVERY_TOKEN = "insert into password_recovery (username, token) values(:username, :token)";

    private static final String DELETE_PASSWORD_RECOVERY_TOKEN = "delete from password_recovery where token = :token ";

    private static final String GET_PASSWORD_RECOVERY_TOKEN = "select * from password_recovery where token = :token";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public PasswordRecovery createPasswordRecoveryToken(String username, String token) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        params.addValue("token", token);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(CREATE_PASSWORD_RECOVERY_TOKEN, params, keyHolder);

        return getPasswordRecovery(keyHolder.getKeys());
    }

    @Override
    public PasswordRecovery deletePasswordRecoveryToken(String token) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("token", token);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(DELETE_PASSWORD_RECOVERY_TOKEN, params, keyHolder);

        return getPasswordRecovery(keyHolder.getKeys());
    }

    @Override
    public PasswordRecovery getPasswordRecoveryToken(String token) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("token", token);

        return jdbcTemplate.queryForObject(GET_PASSWORD_RECOVERY_TOKEN, params, (rs, rowNum) -> {
            return getPasswordRecovery(rs);
        });
    }

    private PasswordRecovery getPasswordRecovery(Map<String, Object> keys) {
        PasswordRecovery passwordRecovery = new PasswordRecovery();

        passwordRecovery.id = (Long) keys.get("id");
        passwordRecovery.username = (String) keys.get("username");
        passwordRecovery.token = (String) keys.get("token");
        passwordRecovery.created = (Date) keys.get("created");

        return passwordRecovery;
    }

    private PasswordRecovery getPasswordRecovery(ResultSet rs) throws SQLException {
        PasswordRecovery passwordRecovery = new PasswordRecovery();

        passwordRecovery.id = rs.getLong("id");
        passwordRecovery.username = rs.getString("username");
        passwordRecovery.token = rs.getString("token");
        passwordRecovery.created = rs.getDate("created");

        return passwordRecovery;
    }
}

