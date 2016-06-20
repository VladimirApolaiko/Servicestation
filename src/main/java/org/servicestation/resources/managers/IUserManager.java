package org.servicestation.resources.managers;

import org.servicestation.resources.dto.UserDto;
import org.servicestation.resources.exceptions.UserAlreadyExists;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.exceptions.ValidationException;

import java.util.List;
import java.util.regex.Pattern;

public interface IUserManager {
    Pattern USERNAME_PATTERN = Pattern.compile(".+@.+");
    Pattern PASSWORD_PATTERN = Pattern.compile("[A-Za-z]+.{0,14}");

    UserDto getUserByUsername(String username) throws UserDoesNotExists;

    UserDto getAdminByStationId(Integer stationId);

    List<UserDto> getAllStationAdmins();

    UserDto registerNewUser(UserDto newUser) throws UserAlreadyExists;

    UserDto registerNemAdmin(UserDto newUser) throws UserAlreadyExists;

    void deleteUserByUsername(String username) throws UserDoesNotExists;

    void changeUserPassword(String username, String oldPassword, String newPassword) throws UserDoesNotExists, ValidationException;

    UserDto changeUser(String username, UserDto newUser) throws UserDoesNotExists;
}
