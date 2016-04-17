package org.servicestation.resources.impl;

import org.servicestation.model.User;
import org.servicestation.resources.UserResource;
import org.servicestation.resources.exceptions.ValidationException;
import org.servicestation.resources.managers.IUserManager;

public class UserResourceImpl implements UserResource {

    @Override
    public void createNewUser(User user) throws ValidationException{
        if (!IUserManager.USERNAME_PATTERN.matcher(user.username).matches())
            throw new ValidationException("Username validation Exception");

        if(!IUserManager.PASSWORD_PATTERN.matcher(user.password).matches()) {
            throw new ValidationException("Password validation Exception");
        }
    }
}
