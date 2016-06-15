package org.servicestation.dao.impl;


import org.servicestation.dao.IAuthoritiesDao;
import org.servicestation.resources.managers.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorityDaoImpl implements IAuthoritiesDao {

    private static final String GRANT_AUTHORITY = "insert into authorities values(:username, :authority);";
    private static final String GET_AUTHORITY = "select authority from authorities where username = :username";
    private static final String REVOKE_AUTHORITY = "delete from authorities where username = :username AND authority = :authority";
    private static final String GET_USERNAMES_BY_AUTHORITY = "select username from authorities where authority = (:authority)";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void grantAuthority(String username, Authority authority) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        params.addValue("authority", authority.toString());

        namedParameterJdbcTemplate.update(GRANT_AUTHORITY, params);
    }

    @Override
    public List<String> getUsernamesByAuthority(String authority) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("authority", authority);


        return namedParameterJdbcTemplate.queryForList(GET_USERNAMES_BY_AUTHORITY, params, String.class);
    }

    @Override
    public void revokeAuthority(String username, Authority authority) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        params.addValue("authority", authority.toString());

        namedParameterJdbcTemplate.update(REVOKE_AUTHORITY, params);
    }

    @Override
    public List<Authority> getAuthoritiesByUsername(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        List<Authority> authorities = new ArrayList<>();

        namedParameterJdbcTemplate.query(GET_AUTHORITY, params, rs -> {
            authorities.add(Authority.valueOf(rs.getString("authority")));
        });

        return authorities;
    }
}
