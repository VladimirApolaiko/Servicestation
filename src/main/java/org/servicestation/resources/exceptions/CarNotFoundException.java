package org.servicestation.resources.exceptions;

public class CarNotFoundException extends EntityNotFoundException {
    public CarNotFoundException() {
    }

    public CarNotFoundException(String message) {
        super(message);
    }

    public CarNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
