package org.servicestation.resources.sokets;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebSocketEventEmitter implements IWebSocketEventEmitter {

    private Map<EventKey, WebSocketEventHandler> eventHandlers = new HashMap<>();


    @Override
    public void registerEventHandler(String username, Session session, WebSocketEvent event, WebSocketEventHandler handler) {
        handler.setSession(session);
        EventKey eventKey = new EventKey(username, event);
        eventHandlers.put(eventKey, handler);
        eventHandlers.putIfAbsent(eventKey, handler);
    }

    @Override
    public void unregisterEventHandler(String username, WebSocketEvent event, WebSocketEventHandler handler) {
        eventHandlers.remove(new EventKey(username, event));
    }

    @Override
    public <T> void emit(String username, WebSocketEvent event, T data) throws IOException {
        WebSocketEventHandler webSocketEventHandler = eventHandlers.get(new EventKey(username, event));
        webSocketEventHandler.handle(event, webSocketEventHandler.getSession(), data);
    }

    @Override
    public boolean isHandlerExists(String username, WebSocketEvent webSocketEvent) {
        return eventHandlers.get(new EventKey(username, webSocketEvent)) != null;
    }
}
