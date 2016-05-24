package org.servicestation.resources.impl;


import org.servicestation.resources.IPasswordRecoveryResource;
import org.servicestation.resources.dto.PasswordRecoveryDto;
import org.servicestation.resources.exceptions.PasswordRecoveryTokenException;
import org.servicestation.resources.managers.IPasswordRecoveryManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;

public class PasswordRecoveryResourceImpl implements IPasswordRecoveryResource {

    @Autowired
    private IPasswordRecoveryManager passwordRecoveryManager;

    @Override
    public Response resetPassword(String username) {
        passwordRecoveryManager.sendPasswordRecoveryEmail(username);
        return Response.ok().build();
    }

    @Override
    public Response setPassword(PasswordRecoveryDto passwordRecovery) throws PasswordRecoveryTokenException {
        passwordRecoveryManager.recoverPassword(passwordRecovery.token, passwordRecovery.password, passwordRecovery.confirmation);
        return Response.ok().build();
    }
}
