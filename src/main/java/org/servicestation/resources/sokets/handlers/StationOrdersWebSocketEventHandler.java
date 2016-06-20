package org.servicestation.resources.sokets.handlers;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.servicestation.resources.dto.FullOrderDto;
import org.servicestation.resources.dto.ResponseSocketMessageDto;
import org.servicestation.resources.dto.WebSocketOrderStationDto;
import org.servicestation.resources.managers.IOrderManager;
import org.servicestation.resources.sokets.WebSocketEvent;
import org.servicestation.resources.sokets.WebSocketEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

public class StationOrdersWebSocketEventHandler extends WebSocketEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(StationOrdersWebSocketEventHandler.class);

    @Autowired
    private IOrderManager orderManager;

    @Override
    public void handle(String username, WebSocketEvent event, Session session, Object data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<FullOrderDto> orders = null;

        WebSocketOrderStationDto dto = null;

        try{
            dto = mapper.readValue((String) (data == null ? "": data), WebSocketOrderStationDto.class);
            if(dto.startDate != null && dto.endDate != null) {
                orders = orderManager.getOrdersByStationId(dto.stationId, dto.startDate, dto.endDate);
            }else {
                orders = orderManager.getOrdersByStationId(dto.stationId);
            }
        }catch(IOException e) {
            LOGGER.info("Can't parse incoming data");
        }

        ResponseSocketMessageDto response = new ResponseSocketMessageDto();
        response.action = event.toString();
        response.data = orders;

        session.getBasicRemote().sendText(mapper.writeValueAsString(response));
    }
}
