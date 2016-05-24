package org.servicestation.resources.managers;

import org.servicestation.resources.exceptions.AccessDeniedException;
import org.servicestation.resources.exceptions.PasswordRecoveryTokenException;
import org.servicestation.resources.exceptions.UserDoesNotExists;
import org.servicestation.resources.exceptions.ValidationException;

public interface IPasswordRecoveryManager {
    void recoverPassword(final String token, final String password, final String confirmation) throws PasswordRecoveryTokenException;
    void sendPasswordRecoveryEmail(final String username);
}
