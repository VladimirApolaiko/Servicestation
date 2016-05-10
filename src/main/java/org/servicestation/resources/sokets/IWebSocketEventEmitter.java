package org.servicestation.resources.sokets;

import javax.websocket.Session;
import java.util.function.BiConsumer;

public interface IWebSocketEventEmitter {
    void registerEventHandler(WebSocketEvent event, Session session, BiConsumer handler);

    void unregisterEventHandler(WebSocketEvent event, BiConsumer handler);

    <T> void emit(WebSocketEvent event, T data);
}
