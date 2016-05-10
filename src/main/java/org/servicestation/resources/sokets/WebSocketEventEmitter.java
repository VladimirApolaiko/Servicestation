package org.servicestation.resources.sokets;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import javax.websocket.Session;
import java.util.function.BiConsumer;

public class WebSocketEventEmitter implements IWebSocketEventEmitter {

    private Multimap<WebSocketEvent, BiConsumer> eventHandlers = HashMultimap.create();

    @Override
    public void registerEventHandler(WebSocketEvent event, Session session, BiConsumer handler) {
        eventHandlers.put(event, handler);
    }

    @Override
    public void unregisterEventHandler(WebSocketEvent event, BiConsumer handler) {
        eventHandlers.remove(event, handler);
    }

    @Override
    public <T> void emit(WebSocketEvent event, T data) {
        eventHandlers.get(event).forEach(handler -> handler.accept(event, data));
    }
}
