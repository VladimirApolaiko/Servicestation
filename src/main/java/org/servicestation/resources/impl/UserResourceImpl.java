package org.servicestation.resources.impl;

import org.servicestation.dao.IUserDao;
import org.servicestation.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserResourceImpl implements UserResource {

    @Autowired
    private IUserDao userDao;


    public String getUserById(Integer id) {
        return userDao.getUserById(id).toString();
    }
}
