package org.servicestation.dao;

import org.servicestation.resources.managers.Authority;

public interface IAuthoritiesDao {

    void grantAuthority(String username, Authority authority);

    void revokeAuthority(String username, Authority authority);

    Authority getAuthorityByUsername(String username);

}
