package org.servicestation.resources.impl;

import org.servicestation.resources.IEmailVerificationResource;
import org.servicestation.resources.exceptions.EmailConfirmationTokenException;
import org.servicestation.resources.managers.IEmailVerificationManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;

public class EmailVerificationResourceImpl implements IEmailVerificationResource {

    @Autowired
    private IEmailVerificationManager emailVerificationManager;


    @Override
    public Response confirmEmail(String token) throws EmailConfirmationTokenException {
        emailVerificationManager.verifyEmail(token);
        return Response.ok().build();
    }
}
