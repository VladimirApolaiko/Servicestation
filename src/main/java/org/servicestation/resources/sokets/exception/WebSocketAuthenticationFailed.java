package org.servicestation.resources.sokets.exception;

public class WebSocketAuthenticationFailed extends RuntimeException{

    public WebSocketAuthenticationFailed() {
    }

    public WebSocketAuthenticationFailed(String message) {
        super(message);
    }
}
