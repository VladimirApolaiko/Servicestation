package org.servicestation.resources.managers;


import org.servicestation.resources.exceptions.EmailConfirmationTokenException;

public interface IEmailVerificationManager {
    void verifyEmail(final String token) throws EmailConfirmationTokenException;

    void sendEmailConfirmation(final String username);
}
