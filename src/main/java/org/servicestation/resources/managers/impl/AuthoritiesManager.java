package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IAuthoritiesDao;
import org.servicestation.dao.IUserDao;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.managers.Authority;
import org.servicestation.resources.managers.IAuthoritiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public class AuthoritiesManager implements IAuthoritiesManager {

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
    public void revokeAuthority(String username, Authority authority) {
        authoritiesDao.revokeAuthority(username, authority);
    }

    @Override
    public List<Authority> getAuthoritiesByUsername(String username) throws UserDoesNotExists {
        try {
            userDao.getUserByUsername(username);
            return authoritiesDao.getAuthoritiesByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username" + username + " not found", e);
        }
    }
}
