package org.servicestation.resources.exceptions;

public class OrderNotFoundException extends EntityNotFoundException{
    public OrderNotFoundException() {
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
