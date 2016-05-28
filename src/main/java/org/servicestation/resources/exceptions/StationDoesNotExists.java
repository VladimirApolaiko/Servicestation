package org.servicestation.resources.exceptions;

public class StationDoesNotExists extends EntityNotFoundException {
    public StationDoesNotExists() {
    }

    public StationDoesNotExists(String message) {
        super(message);
    }

    public StationDoesNotExists(String message, Throwable cause) {
        super(message, cause);
    }
}
