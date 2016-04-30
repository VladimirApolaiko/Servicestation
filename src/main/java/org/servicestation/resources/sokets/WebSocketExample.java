package org.servicestation.resources.sokets;

import org.servicestation.dao.IAuthoritiesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/api/websocket")
public class WebSocketExample {

    @Autowired
    private IAuthoritiesDao iAuthoritiesDao;

    @Autowired
    private ApplicationContext applicationContext;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(String txt, Session session) throws IOException {
        for (int i = 0; i < 10; i++) {
            session.getBasicRemote().sendText(txt.toUpperCase());
        }
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        System.out.println("Closing a WebSocket to " + reason.getReasonPhrase());
    }
}
