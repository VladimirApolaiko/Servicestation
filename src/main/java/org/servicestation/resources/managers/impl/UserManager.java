package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IUserDao;
import org.servicestation.model.User;
import org.servicestation.resources.dto.UserDto;
import org.servicestation.resources.exceptions.*;
import org.servicestation.resources.managers.IEmailVerificationManager;
import org.servicestation.resources.managers.IUserManager;
import org.servicestation.resources.mappers.IObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserManager implements IUserManager {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEmailVerificationManager emailVerificationManager;

    @Autowired
    private IObjectMapper mapper;


    @Override
    public UserDto getUserByUsername(String username) throws UserNotFoundException {
        try {
            return mapper.mapServerObjectToDto(userDao.getUserByUsername(username));
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User with username" + username + " not found", e);
        }
    }

    @Override
    public UserDto registerNewUser(UserDto newUser) throws UserAlreadyExists {
        try {
            newUser.password = passwordEncoder.encode(newUser.password);
            User user = userDao.createUser(mapper.mapDtoToServerObject(newUser));
            emailVerificationManager.sendEmailConfirmation(user.username);
            return mapper.mapServerObjectToDto(user);
        } catch (DuplicateKeyException e) {
            throw new UserAlreadyExists("User with username " + newUser.username + " already exists", e);
        }
    }

    @Override
    public void deleteUserByUsername(String username) throws UserDoesNotExists {
        try {
            userDao.getUserByUsername(username);
            userDao.deleteUserByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username" + username + " not found", e);
        }
    }

    @Override
    public void changeUserPassword(String username, String password, String confirmation) throws UserDoesNotExists, AccessDeniedException, ValidationException {
        try {
            User user = userDao.getUserByUsername(username);
            if (!password.equals(confirmation)) {
                throw new ValidationException("Password and confirmation does not match");
            }
            if (!PASSWORD_PATTERN.matcher(confirmation).matches()) {
                throw new ValidationException("Password validation Exception");
            }
            user.password = passwordEncoder.encode(password);
            userDao.changeUserByUsername(username, user);
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username" + username + " not found", e);
        }
    }

    @Override
    public UserDto changeUser(String username, UserDto newUser) throws UserDoesNotExists {
        try {
            return mapper.mapServerObjectToDto(userDao.changeUserByUsername(username, mapper.mapDtoToServerObject(newUser)));
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username" + username + " not found", e);
        }
    }

}
