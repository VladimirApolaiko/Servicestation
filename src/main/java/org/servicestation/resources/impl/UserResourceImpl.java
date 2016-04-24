package org.servicestation.resources.impl;

import org.servicestation.model.User;
import org.servicestation.resources.UserResource;
import org.servicestation.resources.exceptions.UserAlreadyExists;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.exceptions.ValidationException;
import org.servicestation.resources.managers.Authority;
import org.servicestation.resources.managers.IAuthoritiesManager;
import org.servicestation.resources.managers.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;

public class UserResourceImpl implements UserResource {

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IAuthoritiesManager authoritiesManager;

    @Override
    public void createNewUser(User user) throws ValidationException, UserAlreadyExists, UserDoesNotExists {
        if (!IUserManager.USERNAME_PATTERN.matcher(user.username).matches())
            throw new ValidationException("Username validation Exception");

        if(!IUserManager.PASSWORD_PATTERN.matcher(user.password).matches()) {
            throw new ValidationException("Password validation Exception");
        }

        userManager.registerNewUser(user);
        authoritiesManager.grantAuthority(user.username, Authority.ROLE_USER);
    }
}
