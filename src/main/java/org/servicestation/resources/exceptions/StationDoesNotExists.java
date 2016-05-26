package org.servicestation.resources.exceptions;

public class StationDoesNotExists extends Exception {
    public StationDoesNotExists() {
    }

    public StationDoesNotExists(String message) {
        super(message);
    }
}
