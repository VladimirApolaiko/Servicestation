package org.servicestation.test;


import org.junit.Test;
import org.servicestation.dao.IAuthoritiesDao;
import org.servicestation.resources.managers.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

import java.util.List;

public class AuthoritiesDaoTest extends AbstractTest {

    @Autowired
    private IAuthoritiesDao authoritiesDao;

    @Test
    public void testGrantRole() {
        final String username = "test_user_foreign_3";
        Authority authority = Authority.ROLE_USER;
        authoritiesDao.grantAuthority(username, authority);
        List<Authority> authorities = authoritiesDao.getAuthoritiesByUsername(username);
        assertTrue(authorities.contains(authority));
    }

   /* @Test
    public void testRevokeRole() {
        final String username = "test_user_foreign_1";
        *//*final Authority authorityToRevoke = Authority.ROLE_MECHANIC;*//*
        authoritiesDao.revokeAuthority(username, authorityToRevoke);

        List<Authority> authorities = authoritiesDao.getAuthoritiesByUsername(username);
        assertTrue(!authorities.contains(authorityToRevoke));
    }*/

    @Test
    public void testGetAllAuthorities() {
        final String username = "test_user_foreign_2";
        final int numberOfAuthorities = 2;

        List<Authority> authorities = authoritiesDao.getAuthoritiesByUsername(username);
        assertEquals(numberOfAuthorities, authorities.size());
    }


}
