package org.servicestation.resources.exceptions;

public class StationOrderAlreadyExistsException extends EntityAlreadyExists {

    public StationOrderAlreadyExistsException() {
    }

    public StationOrderAlreadyExistsException(String message) {
        super(message);
    }
}
