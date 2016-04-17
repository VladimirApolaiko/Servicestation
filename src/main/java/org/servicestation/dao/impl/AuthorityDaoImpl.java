package org.servicestation.dao.impl;


import org.servicestation.dao.IAuthoritiesDao;
import org.servicestation.resources.managers.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class AuthorityDaoImpl implements IAuthoritiesDao {

    private static final String GRANT_AUTHORITY = "insert into authorities values(:username, :authority);";
    private static final String GET_AUTHORITY = "select authority from authorities where username = :username";
    private static final String REVOKE_AUTHORITY = "delete * from authorities where username = :username AND authority = :authority";


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
    public void revokeAuthority(String username, Authority authority) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        params.addValue("authority", authority.toString());

        namedParameterJdbcTemplate.update(REVOKE_AUTHORITY, params);
    }

    @Override
    public Authority getAuthorityByUsername(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        return Authority.valueOf(namedParameterJdbcTemplate.queryForObject(GET_AUTHORITY, params, (rs, rowNum) -> {
            return rs.getString("authority");
        }));
    }
}
