package org.servicestation.resources.sokets.handlers;


import org.codehaus.jackson.map.ObjectMapper;
import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.WebSocketOrderUserDto;
import org.servicestation.resources.managers.IOrderManager;
import org.servicestation.resources.sokets.WebSocketEvent;
import org.servicestation.resources.sokets.WebSocketEventHandler;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

public class UserOrdersWebSocketEventHandler extends WebSocketEventHandler {

    @Autowired
    private IOrderManager orderManager;

    @Override
    public void handle(String username, WebSocketEvent event, Session session, Object data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<FullOrderDto> orders;

        WebSocketOrderUserDto dto = mapper.readValue((String) data, WebSocketOrderUserDto.class);
        if(dto.startDate != null && dto.endDate != null) {
            orders = orderManager.getOrdersByUsername(username, dto.startDate, dto.endDate);
        }else {
            orders = orderManager.getOrdersByUsername(username);
        }

        session.getBasicRemote().sendText(mapper.writeValueAsString(orders));
    }
}
