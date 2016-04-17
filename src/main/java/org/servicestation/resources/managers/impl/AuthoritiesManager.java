package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IAuthoritiesDao;
import org.servicestation.dao.IUserDao;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.managers.Authority;
import org.servicestation.resources.managers.IAuthoritiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

public class AuthoritiesManager implements IAuthoritiesManager{

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IAuthoritiesDao authoritiesDao;

    @Override
    public void grantAuthority(String username, Authority authority) throws UserDoesNotExists {
        try {
            userDao.getUserByUsername(username);
            authoritiesDao.grantAuthority(username, authority);
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username" + username + " not found", e);
        }
    }

    @Override
    public void rejectAuthority(String username, Authority authority) {

    }

    @Override
    public Authority getAuthorityByUsername(String username) {
        return null;
    }
}
