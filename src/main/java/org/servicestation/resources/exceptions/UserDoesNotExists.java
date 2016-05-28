package org.servicestation.resources.exceptions;


public class UserDoesNotExists extends EntityNotFoundException {
    public UserDoesNotExists() {
    }

    public UserDoesNotExists(String message) {
        super(message);
    }

    public UserDoesNotExists(String message, Throwable cause) {
        super(message, cause);
    }
}
