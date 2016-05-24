package org.servicestation.resources.exceptions;


public class PasswordRecoveryTokenException extends Exception {

    public PasswordRecoveryTokenException() {
        super();
    }

    public PasswordRecoveryTokenException(String message) {
        super(message);
    }
}
