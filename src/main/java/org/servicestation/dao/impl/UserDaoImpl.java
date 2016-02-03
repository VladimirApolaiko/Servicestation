package org.servicestation.dao.impl;


import org.servicestation.User;
import org.servicestation.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class UserDaoImpl implements IUserDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String GET_USER_BY_ID = "select * from users where id = :id";

    @Override
    public User getUserById(Integer id) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbcTemplate.queryForObject(GET_USER_BY_ID, params, (rs, rowNum) -> {
            return new User(rs.getInt("id"), rs.getString("name"));
        });
    }
}

