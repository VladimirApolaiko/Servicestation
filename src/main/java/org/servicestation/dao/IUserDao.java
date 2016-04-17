package org.servicestation.dao;

import org.servicestation.model.User;

public interface IUserDao {
    User createUser(User user);

    User changeUserByUsername(String username, User user) throws Exception;

    User deleteUserByUsername(String username);

    User getUserByUsername(String username);
}
