package org.servicestation.dao.impl;


import org.servicestation.User;
import org.servicestation.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements IUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String GET_USER_BY_ID = "select * from users where id = ?";

    @Override
    public User getUserById(Integer id) {

        return jdbcTemplate.queryForObject(GET_USER_BY_ID, (rs, rowNum) -> {
            return new User(rs.getInt(id), rs.getString("name"));
        }, id);
    }
}

