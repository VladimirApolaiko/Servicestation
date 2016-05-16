package org.servicestation.resources.sokets;

import javax.websocket.Session;
import java.io.IOException;

public abstract class WebSocketEventHandler {

    private Session session;

    public abstract void handle(WebSocketEvent event, Session session, Object data) throws IOException;

    public void setSession(Session session) {
        this.session = session;
    };

    public Session getSession() {
        return this.session;
    };
}
