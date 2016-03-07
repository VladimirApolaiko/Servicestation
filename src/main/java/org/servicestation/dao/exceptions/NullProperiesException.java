package org.servicestation.dao.exceptions;

public class NullProperiesException extends Exception {
    public NullProperiesException(String message) {
        super(message);
    }

    public NullProperiesException(String message, Throwable cause) {
        super(message, cause);
    }
}
