package org.servicestation.resources.sokets;

import org.servicestation.resources.managers.Authority;

import javax.websocket.Session;
import java.io.IOException;

public interface IWebSocketEventEmitter {
    void registerEventHandler(String username, Session session, WebSocketEvent event, WebSocketEventHandler handler);

    void unregisterEventHandler(String username, String sessionId, WebSocketEvent event, WebSocketEventHandler handler);

    <T> void emit(String username, WebSocketEvent event, T data) throws IOException;

    <T> void emitForAuthorities(Authority authority, WebSocketEvent event, T data) throws IOException;

}
