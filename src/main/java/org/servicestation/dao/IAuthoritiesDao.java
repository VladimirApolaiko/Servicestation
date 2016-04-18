package org.servicestation.dao;

import org.servicestation.resources.managers.Authority;

import java.util.List;

public interface IAuthoritiesDao {

    void grantAuthority(String username, Authority authority);

    void revokeAuthority(String username, Authority authority);

    List<Authority> getAuthoritiesByUsername(String username);

}
