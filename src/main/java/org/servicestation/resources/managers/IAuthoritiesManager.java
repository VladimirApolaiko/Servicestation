package org.servicestation.resources.managers;


import org.servicestation.resources.exceptions.UserDoesNotExists;

public interface IAuthoritiesManager {
    void grantAuthority(String username, Authority authority) throws UserDoesNotExists;
    void rejectAuthority(String username, Authority authority);
    Authority getAuthorityByUsername(String username);
}
