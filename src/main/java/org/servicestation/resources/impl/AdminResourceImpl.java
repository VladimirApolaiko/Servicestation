package org.servicestation.resources.impl;

import org.servicestation.model.AdminStation;
import org.servicestation.resources.IAdminResource;
import org.servicestation.resources.dto.UserDto;
import org.servicestation.resources.exceptions.UserAlreadyExists;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.exceptions.ValidationException;
import org.servicestation.resources.managers.Authority;
import org.servicestation.resources.managers.IAdminStationManager;
import org.servicestation.resources.managers.IAuthoritiesManager;
import org.servicestation.resources.managers.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

public class AdminResourceImpl implements IAdminResource{

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IAuthoritiesManager authoritiesManager;

    @Autowired
    private IAdminStationManager adminStationManager;

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
    public List<UserDto> getAllStationAdmins() {
        return userManager.getAllStationAdmins();
    }

    @Override
    public UserDto getStationAdmin(SecurityContext securityContext) {
        return userManager.getAdminByUsername(securityContext.getUserPrincipal().getName());
    }
    @Override
    public UserDto changeAdmin(String username, UserDto newUser) throws UserDoesNotExists {
        List<Authority> authoritiesByUsername = authoritiesManager.getAuthoritiesByUsername(username);
        if(!authoritiesByUsername.contains(Authority.ROLE_STATION_ADMIN)){
            throw new AccessDeniedException("User is not station admin");
        }

        return userManager.changeUser(username, newUser);
    }

    @Override
    public void deleteAdmin(String username) throws UserDoesNotExists {
        List<Authority> authoritiesByUsername = authoritiesManager.getAuthoritiesByUsername(username);
        if(!authoritiesByUsername.contains(Authority.ROLE_STATION_ADMIN)) {
            throw new AccessDeniedException("User is not station admin");
        }
        authoritiesManager.revokeAuthority(username, Authority.ROLE_STATION_ADMIN);
        AdminStation adminStationByUsername = adminStationManager.getAdminStationByUsername(username);
        adminStationManager.unassignAdmin(username, adminStationByUsername.stationId);
        userManager.deleteUserByUsername(username);
    }
}
