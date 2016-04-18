package org.servicestation.resources.managers;


import org.servicestation.resources.exceptions.UserDoesNotExists;

import java.util.List;

public interface IAuthoritiesManager {
    void grantAuthority(String username, Authority authority) throws UserDoesNotExists;
    void revokeAuthority(String username, Authority authority);
    List<Authority> getAuthoritiesByUsername(String username) throws UserDoesNotExists;
}
