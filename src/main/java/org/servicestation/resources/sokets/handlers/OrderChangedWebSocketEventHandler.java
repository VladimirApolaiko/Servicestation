package org.servicestation.resources.sokets.handlers;

import org.codehaus.jackson.map.ObjectMapper;
import org.servicestation.resources.dto.ResponseSocketMessageDto;
import org.servicestation.resources.sokets.WebSocketEvent;
import org.servicestation.resources.sokets.WebSocketEventHandler;

import javax.websocket.Session;
import java.io.IOException;

public class OrderChangedWebSocketEventHandler extends WebSocketEventHandler {

    @Override
    public void handle(String username, WebSocketEvent event, Session session, Object data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ResponseSocketMessageDto dto = new ResponseSocketMessageDto();

        dto.action = event.toString();
        session.getBasicRemote().sendText(mapper.writeValueAsString(dto));
    }
}