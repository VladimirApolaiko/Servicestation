package org.servicestation.resources.managers;

import org.servicestation.model.User;
import org.servicestation.resources.exceptions.*;

import java.util.regex.Pattern;

public interface IUserManager {
    Pattern USERNAME_PATTERN = Pattern.compile(".+@.+");
    Pattern PASSWORD_PATTERN = Pattern.compile("[A-Za-z]+.{0,14}");

    User getUserByUsername(String username) throws UserNotFoundException;
    void registerNewUser(User newUser) throws UserAlreadyExists;
    void deleteUserByUsername(String username) throws UserDoesNotExists;
    void changeUserPassword(String username, String oldPassword, String newPassword) throws UserDoesNotExists, AccessDeniedException, ValidationException;
    void changeUsername(String username, String newUsername) throws UserDoesNotExists;
}
