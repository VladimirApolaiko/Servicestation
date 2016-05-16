package org.servicestation.resources.sokets;

import javax.websocket.Session;
import java.io.IOException;
import java.util.function.BiConsumer;

public interface IWebSocketEventEmitter {
    void registerEventHandler(String username, Session session, WebSocketEvent event, WebSocketEventHandler handler);

    void unregisterEventHandler(String username, WebSocketEvent event, WebSocketEventHandler handler);

    <T> void emit(String username, WebSocketEvent event, T data) throws IOException;

    boolean isHandlerExists(String username, WebSocketEvent webSocketEvent);
}
