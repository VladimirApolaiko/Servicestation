package org.servicestation.test;

import org.junit.Test;
import org.servicestation.dao.IUserDao;
import org.servicestation.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class UserDaoTest extends AbstractTest {

    @Autowired
    private IUserDao iUserDao;

    @Test
    public void testCreateUser() {

        final User newUser = new User();
        newUser.username = "test_new_user";
        newUser.password = "test_new_user";
        newUser.enabled = false;
        newUser.firstname = "test_new_user_firstname";
        newUser.lastname = "test_new_user_lastname";
        newUser.phone_number = "+37588996688";

        final User createdUser = iUserDao.createUser(newUser);

        assertEquals(newUser.username, createdUser.username);
        assertEquals(newUser.password, createdUser.password);
        assertEquals(newUser.enabled, createdUser.enabled);
        assertEquals(newUser.firstname, createdUser.firstname);
        assertEquals(newUser.lastname, createdUser.lastname);
        assertEquals(newUser.phone_number, createdUser.phone_number);
    }

    @Test
    public void testChangeUser() {
        final User newUser = new User();
        newUser.username = "test_change_user_username";
        newUser.password = "test_change_user_password";
        newUser.enabled = true;
        newUser.firstname = "test_change_user_firstname";
        newUser.lastname = "test_change_user_lastname";
        newUser.phone_number = "+37533888999666";

        final String username = "test_change_user";
        final User changedUser = iUserDao.changeUserByUsername(username, newUser);

        assertEquals(newUser.password, changedUser.password);
        assertEquals(newUser.enabled, changedUser.enabled);
        assertEquals(newUser.firstname, changedUser.firstname);
        assertEquals(newUser.lastname, changedUser.lastname);
        assertEquals(newUser.phone_number, changedUser.phone_number);
    }

    @Test
    public void testDeleteUser() {
        final String username = "test_delete_user";
        final User deletedUser = iUserDao.deleteUserByUsername(username);

        assertEquals(username, deletedUser.username);
    }

    @Test
    public void testGetUser() {
        final String username = "test_user";
        final String password = "test_user";
        final boolean enabled = true;
        final String firstName = "Test3";
        final String lastName = "User3";

        User user = iUserDao.getUserByUsername(username);

        assertEquals(username, user.username);
        assertEquals(password, user.password);
        assertEquals(enabled, user.enabled);
        assertEquals(firstName, user.firstname);
        assertEquals(lastName, user.lastname);
    }
}
