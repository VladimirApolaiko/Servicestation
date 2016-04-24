package org.servicestation.dao.exceptions;

public class NullPropertiesException extends RuntimeException {
    public NullPropertiesException(String message) {
        super(message);
    }

    public NullPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }
}
