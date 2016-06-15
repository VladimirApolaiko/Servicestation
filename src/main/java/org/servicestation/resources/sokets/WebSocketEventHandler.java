package org.servicestation.resources.sokets;

import javax.websocket.Session;
import java.io.IOException;

public abstract class WebSocketEventHandler {

    private Session session;

    private WebSocketEvent action;

    public abstract void handle(String username, WebSocketEvent event, Session session, Object data) throws IOException;

    public void setSession(Session session) {
        this.session = session;
    };

    public void setAction(WebSocketEvent action) {
        this.action = action;
    }

    public Session getSession() {
        return this.session;
    };

    public WebSocketEvent getAction() {
        return action;
    }
}
