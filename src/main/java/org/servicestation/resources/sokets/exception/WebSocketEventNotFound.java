package org.servicestation.resources.sokets.exception;


public class WebSocketEventNotFound extends RuntimeException {

    public WebSocketEventNotFound() {
    }

    public WebSocketEventNotFound(String message) {
        super(message);
    }
}
