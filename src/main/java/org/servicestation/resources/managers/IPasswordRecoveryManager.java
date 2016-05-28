package org.servicestation.resources.managers;

import org.servicestation.resources.exceptions.PasswordRecoveryTokenException;

public interface IPasswordRecoveryManager {
    void recoverPassword(final String token, final String password, final String confirmation) throws PasswordRecoveryTokenException;

    void sendPasswordRecoveryEmail(final String username);
}
