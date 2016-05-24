package org.servicestation.resources.managers.impl;

import org.servicestation.dao.IUserDao;
import org.servicestation.model.User;
import org.servicestation.resources.exceptions.*;
import org.servicestation.resources.managers.IEmailVerificationManager;
import org.servicestation.resources.managers.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserManager implements IUserManager{

    @Autowired
    private IUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEmailVerificationManager emailVerificationManager;


    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
       try {
           return userDao.getUserByUsername(username);
       }catch(EmptyResultDataAccessException e) {
           throw new UserNotFoundException("User with username" + username + " not found", e);
       }
    }

    @Override
    public void registerNewUser(User newUser) throws UserAlreadyExists {
        try {
            newUser.password = passwordEncoder.encode(newUser.password);
            User user = userDao.createUser(newUser);
            emailVerificationManager.sendEmailConfirmation(user.username);
        } catch(DuplicateKeyException e){
            throw new UserAlreadyExists("User with username " + newUser.username + " already exists", e);
        }
    }

    @Override
    public void deleteUserByUsername(String username) throws UserDoesNotExists {
        try {
            userDao.getUserByUsername(username);
            userDao.deleteUserByUsername(username);
        } catch(EmptyResultDataAccessException e) {
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
            if(!PASSWORD_PATTERN.matcher(confirmation).matches()) {
                throw new ValidationException("Password validation Exception");
            }
            user.password = passwordEncoder.encode(password);
            userDao.changeUserByUsername(username, user);
        } catch (EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username" + username + " not found", e);
        }
    }

    @Override
    public void changeUsername(String username, String newUsername) throws UserDoesNotExists {
        try {
            User user = userDao.getUserByUsername(username);
            user.username = newUsername;
            userDao.changeUserByUsername(username, user);
        } catch(EmptyResultDataAccessException e) {
            throw new UserDoesNotExists("User with username" + username + " not found", e);
        }
    }

}
