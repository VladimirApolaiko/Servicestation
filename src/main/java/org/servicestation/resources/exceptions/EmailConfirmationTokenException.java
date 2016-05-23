package org.servicestation.resources.exceptions;

public class EmailConfirmationTokenException extends Exception{

    public EmailConfirmationTokenException() {
        super();
    }

    public EmailConfirmationTokenException(String message) {
        super(message);
    }
}
