package org.servicestation.resources.sokets.exception;


public class WebSocketMessageParseException extends RuntimeException {

    public WebSocketMessageParseException() {
        super();
    }

    public WebSocketMessageParseException(String message) {
        super(message);
    }
}
