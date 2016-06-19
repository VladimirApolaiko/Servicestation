package org.servicestation.resources.impl;

import org.servicestation.resources.IAuthoritiesResource;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.managers.Authority;
import org.servicestation.resources.managers.IAuthoritiesManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.SecurityContext;
import java.util.List;

public class AuthoritiesResourceImpl implements IAuthoritiesResource {

    @Autowired
    private IAuthoritiesManager authoritiesManager;

    @Override
    public List<Authority> getAuthorities(SecurityContext securityContext) throws UserDoesNotExists {
        return authoritiesManager.getAuthoritiesByUsername(securityContext.getUserPrincipal().getName());
    }
}
