package org.servicestation.resources.sokets;

import org.servicestation.dao.IAuthoritiesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/api/websocket")
public class WebSocketExample {

    @Autowired
    private IAuthoritiesDao iAuthoritiesDao;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WebSocketEventEmitter eventEmitter;

    @Autowired
    private TokenStore tokenStore;

    @OnOpen
    public void onOpen(Session session) {
       eventEmitter.registerEventHandler(WebSocketEvent.GET_ALL_ORDERS, session, (event, data) -> {
           try {
               session.getBasicRemote().sendText("Web socket event: " + event.toString());
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
    }

    @OnMessage
    public void onMessage(String txt, Session session) throws IOException {
       try{
           WebSocketEvent webSocketEvent = WebSocketEvent.valueOf(txt);
           eventEmitter.emit(webSocketEvent, null);
       } catch(IllegalArgumentException e) {
           throw new WebSocketHandlersNotFound("Event " + txt + "not found");
       }
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        System.out.println("Closing a WebSocket to " + reason.getReasonPhrase());
    }
}
