package org.servicestation.config.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Principal;

public class CustomPreAuthUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public final UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) {
        return userDetailsService.loadUserByUsername(((Principal)token.getPrincipal()).getName());
    }
}