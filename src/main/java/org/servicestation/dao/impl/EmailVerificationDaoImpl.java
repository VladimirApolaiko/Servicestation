package org.servicestation.dao.impl;


import org.servicestation.dao.IEmailVerificationDao;
import org.servicestation.model.EmailVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EmailVerificationDaoImpl implements IEmailVerificationDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String CREATE_EMAIL_VERIFICATION_TOKEN = "insert into email_verification (username, token) values(:username, :token)";

    private static final String DELETE_EMAIL_VERIFICATION_TOKEN_BY_USER = "delete from email_verification where username = :username";

    private static final String GET_ALL_VERIFICATION_TOKENS = "select * from email_verification where token = :token";

    @Override
    public EmailVerification createEmailVerificationToken(String username, String token) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        params.addValue("token", token);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(CREATE_EMAIL_VERIFICATION_TOKEN, params, keyHolder);

        return getEmailVerification(keyHolder.getKeys());
    }

    @Override
    public void deleteAllVerificationTokens(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        jdbcTemplate.update(DELETE_EMAIL_VERIFICATION_TOKEN_BY_USER, params);
    }

    @Override
    public EmailVerification getVerification(String token) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("token", token);

        return jdbcTemplate.queryForObject(GET_ALL_VERIFICATION_TOKENS, params, (rs, rowNum) -> {
            return getEmailVerification(rs);
        });
    }

    private EmailVerification getEmailVerification(Map<String, Object> keys) {
        if (keys == null) return null;

        EmailVerification emailVerification = new EmailVerification();
        emailVerification.id = (long) keys.get("id");
        emailVerification.username = (String) keys.get("username");
        emailVerification.token = (String) keys.get("token");
        emailVerification.created = (Date) keys.get("created");

        return emailVerification;
    }

    private EmailVerification getEmailVerification(ResultSet rs) throws SQLException {
        EmailVerification emailVerification = new EmailVerification();

        emailVerification.id = rs.getLong("id");
        emailVerification.username = rs.getString("username");
        emailVerification.token = rs.getString("token");
        emailVerification.created = rs.getDate("created");

        return emailVerification;

    }
}
