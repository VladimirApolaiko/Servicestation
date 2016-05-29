package org.servicestation.resources.exceptions;

public class EntityAlreadyExists extends Exception{

    public EntityAlreadyExists() {
    }

    public EntityAlreadyExists(String message) {
        super(message);
    }
}
