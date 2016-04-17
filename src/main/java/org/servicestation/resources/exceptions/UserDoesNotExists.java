package org.servicestation.resources.exceptions;


public class UserDoesNotExists extends Exception{

    public UserDoesNotExists(String message) {
        super(message);
    }

    public UserDoesNotExists(String message, Throwable cause) {
        super(message, cause);
    }
}
