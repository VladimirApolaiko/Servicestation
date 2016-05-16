package org.servicestation.resources.sokets;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class WebSocketEventEmitter implements IWebSocketEventEmitter {

    private Map<EventKey, BiConsumer> eventHandlers = new HashMap<>();

    @Override
    public void registerEventHandler(String username, WebSocketEvent event, BiConsumer handler) {
        eventHandlers.put(new EventKey(username, event), handler);
    }

    @Override
    public void unregisterEventHandler(String username, WebSocketEvent event, BiConsumer handler) {
        eventHandlers.remove(new EventKey(username, event), handler);
    }

    @Override
    public <T> void emit(String username, WebSocketEvent event, T data) {
        eventHandlers.get(new EventKey(username, event)).accept(event, data);
    }

    @Override
    public boolean isHandlerExists(String username, WebSocketEvent webSocketEvent) {
        return eventHandlers.get(new EventKey(username, webSocketEvent)) != null;
    }
}
