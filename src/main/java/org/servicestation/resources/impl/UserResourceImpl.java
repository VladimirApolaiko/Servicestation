package org.servicestation.resources.impl;

import org.servicestation.resources.IUserResource;
import org.servicestation.resources.dto.UserDto;
import org.servicestation.resources.exceptions.UserAlreadyExists;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.exceptions.ValidationException;
import org.servicestation.resources.managers.Authority;
import org.servicestation.resources.managers.IAuthoritiesManager;
import org.servicestation.resources.managers.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.core.SecurityContext;

public class UserResourceImpl implements IUserResource {

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IAuthoritiesManager authoritiesManager;


    @Override
    public UserDto createNewUser(UserDto user) throws ValidationException, UserAlreadyExists, UserDoesNotExists {
        if (!IUserManager.USERNAME_PATTERN.matcher(user.username).matches())
            throw new ValidationException("Username validation Exception");

        if (!IUserManager.PASSWORD_PATTERN.matcher(user.password).matches()) {
            throw new ValidationException("Password validation Exception");
        }

        UserDto userDto = userManager.registerNewUser(user);
        authoritiesManager.grantAuthority(userDto.username, Authority.ROLE_USER);
        return userDto;
    }

    @Override
    public UserDto createNewStationAdmin(UserDto user) throws UserDoesNotExists, UserAlreadyExists, ValidationException {
        if (!IUserManager.USERNAME_PATTERN.matcher(user.username).matches())
            throw new ValidationException("Username validation Exception");

        if (!IUserManager.PASSWORD_PATTERN.matcher(user.password).matches()) {
            throw new ValidationException("Password validation Exception");
        }

        UserDto userDto = userManager.registerNemAdmin(user);
        authoritiesManager.grantAuthority(userDto.username, Authority.ROLE_STATION_ADMIN);

        return userDto;
    }

    @Override
    public UserDto changeUser(UserDto user, SecurityContext securityContext) throws UserDoesNotExists {
        return userManager.changeUser(securityContext.getUserPrincipal().getName(), user);
    }

    @Override
    public UserDto getUser(SecurityContext securityContext) throws UserDoesNotExists {
        return userManager.getUserByUsername(securityContext.getUserPrincipal().getName());
    }
}
