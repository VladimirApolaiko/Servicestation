package org.servicestation.resources.sokets.handlers;

import org.servicestation.resources.sokets.WebSocketEvent;
import org.servicestation.resources.sokets.WebSocketEventHandler;

import javax.websocket.Session;
import java.io.IOException;

public class OrderChangedWebSocketEventHandler extends WebSocketEventHandler {

    @Override
    public void handle(String username, WebSocketEvent event, Session session, Object data) throws IOException {
        session.getBasicRemote().sendText(event.toString());
    }
}