package org.servicestation.resources.sokets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.servicestation.dao.IAuthoritiesDao;
import org.servicestation.resources.sokets.dto.SocketMessage;
import org.servicestation.resources.sokets.exception.WebSocketAuthenticationFailed;
import org.servicestation.resources.sokets.exception.WebSocketEventNotFound;
import org.servicestation.resources.sokets.exception.WebSocketMessageParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.websocket.*;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/api/websocket")
public class WebSocketExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketExample.class);

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

    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SocketMessage socketMessage = objectMapper.readValue(message, SocketMessage.class);

            OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(socketMessage.accessToken);
            if (oAuth2Authentication == null)
                throw new WebSocketAuthenticationFailed("Authentication with access token " + socketMessage.accessToken + " not found");

            WebSocketEvent webSocketEvent = WebSocketEvent.valueOf(socketMessage.action);
 
            String username = oAuth2Authentication.getName();

            if(!eventEmitter.isHandlerExists(username, WebSocketEvent.GET_ALL_ORDERS)){
                eventEmitter.registerEventHandler(username, WebSocketEvent.GET_ALL_ORDERS, (event, data) -> {
                    try {
                        session.getBasicRemote().sendText("Hello");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }



            eventEmitter.emit(oAuth2Authentication.getName(), webSocketEvent, socketMessage.data);
        } catch (IllegalArgumentException e) {
            final String errorMessage = "Can't find specified action";
            session.close(new CloseReason(CloseCodes.CANNOT_ACCEPT, errorMessage));
            throw new WebSocketEventNotFound(errorMessage);
        } catch (IOException e) {
            final String errorMessage = "Can't parse incoming web socket message";
            session.close(new CloseReason(CloseCodes.CANNOT_ACCEPT, errorMessage));
            throw new WebSocketMessageParseException(errorMessage);
        } catch (WebSocketAuthenticationFailed e) {
            session.close(new CloseReason(CloseCodes.CANNOT_ACCEPT, e.getMessage()));
            throw e;
        }

    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        System.out.println("Closing a WebSocket to " + reason.getReasonPhrase());
    }

    @OnError
    public void onError(Throwable e) {
        LOGGER.warn(e.getMessage());
    }
}
