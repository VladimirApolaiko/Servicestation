package org.servicestation.resources.sokets;

import javax.websocket.Session;
import java.util.function.BiConsumer;

public interface IWebSocketEventEmitter {
    void registerEventHandler(String username, WebSocketEvent event, BiConsumer handler);

    void unregisterEventHandler(String username, WebSocketEvent event, BiConsumer handler);

    <T> void emit(String username, WebSocketEvent event, T data);

    boolean isHandlerExists(String username, WebSocketEvent webSocketEvent);
}
