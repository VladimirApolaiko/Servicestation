package org.servicestation.dao;

import org.servicestation.model.User;

import java.util.List;

public interface IUserDao {
    User createUser(User user);

    User changeUserByUsername(String username, User user);

    User deleteUserByUsername(String username);

    User getUserByUsername(String username);

    List<User> getAllStationAdmins();

    User getAdminStation(Integer stationId);
}
