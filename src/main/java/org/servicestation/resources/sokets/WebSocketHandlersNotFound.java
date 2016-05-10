package org.servicestation.resources.sokets;


public class WebSocketHandlersNotFound extends RuntimeException {

    public WebSocketHandlersNotFound() {
    }

    public WebSocketHandlersNotFound(String message) {
        super(message);
    }
}
