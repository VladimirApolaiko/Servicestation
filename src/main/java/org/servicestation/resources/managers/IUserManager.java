package org.servicestation.resources.managers;

import org.servicestation.resources.dto.UserDto;
import org.servicestation.resources.exceptions.*;

import java.util.regex.Pattern;

public interface IUserManager {
    Pattern USERNAME_PATTERN = Pattern.compile(".+@.+");
    Pattern PASSWORD_PATTERN = Pattern.compile("[A-Za-z]+.{0,14}");

    UserDto getUserByUsername(String username) throws UserNotFoundException;

    UserDto registerNewUser(UserDto newUser) throws UserAlreadyExists;

    void deleteUserByUsername(String username) throws UserDoesNotExists;

    void changeUserPassword(String username, String oldPassword, String newPassword) throws UserDoesNotExists, AccessDeniedException, ValidationException;

    UserDto changeUser(String username, UserDto newUser) throws UserDoesNotExists;
}
