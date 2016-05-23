package org.servicestation.resources.impl;

import org.servicestation.resources.EmailVerificationResource;
import org.servicestation.resources.exceptions.EmailConfirmationTokenException;
import org.servicestation.resources.managers.EmailVerificationManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;

public class EmailVerificationResourceImpl implements EmailVerificationResource {

    @Autowired
    private EmailVerificationManager emailVerificationManager;


    @Override
    public Response confirmEmail(String token) throws EmailConfirmationTokenException {
        emailVerificationManager.verifyEmail(token);

        return Response.ok().build();
    }
}
