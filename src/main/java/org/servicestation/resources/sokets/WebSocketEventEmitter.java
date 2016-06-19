package org.servicestation.resources.sokets;

import org.servicestation.dao.IAuthoritiesDao;
import org.servicestation.dao.IUserDao;
import org.servicestation.resources.managers.Authority;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebSocketEventEmitter implements IWebSocketEventEmitter {

    @Autowired
    private IAuthoritiesDao authoritiesDao;

    private Map<String, HashMap<EventKey, WebSocketEventHandler>> eventHandlers = new HashMap<>();

    @Override
    public void registerEventHandler(String username, Session session, WebSocketEvent event, WebSocketEventHandler handler) {
        handler.setSession(session);

        HashMap<EventKey, WebSocketEventHandler> usersEventHandlers = eventHandlers.get(username);
        EventKey eventKey = new EventKey(session.getId(), event);

        if (usersEventHandlers == null) {
            usersEventHandlers = new HashMap<>();

            usersEventHandlers.put(eventKey, handler);
            eventHandlers.put(username, usersEventHandlers);
        } else {
            WebSocketEventHandler webSocketEventHandler = usersEventHandlers.get(eventKey);
            if (webSocketEventHandler == null) {
                usersEventHandlers.put(eventKey, handler);
            }
        }
    }

    @Override
    public void unregisterEventHandler(String username, WebSocketEvent event, WebSocketEventHandler handler) {
        eventHandlers.remove(new EventKey(username, event), handler);
    }

    @Override
    public <T> void emit(String username, WebSocketEvent event, T data) throws IOException {
        HashMap<EventKey, WebSocketEventHandler> eventKeyWebSocketEventHandlerMultimap = eventHandlers.get(username);

        if(eventKeyWebSocketEventHandlerMultimap != null){
            List<WebSocketEventHandler> webSocketEventHandlers = eventKeyWebSocketEventHandlerMultimap.entrySet().stream()
                    .filter(e -> e.getKey().event.equals(event))
                    .map(Map.Entry::getValue).collect(Collectors.toList());

            for (WebSocketEventHandler webSocketEventHandler : webSocketEventHandlers) {
                webSocketEventHandler.handle(username, event, webSocketEventHandler.getSession(), data);
            }
        }
    }

    public <T> void emitForAuthorities(Authority authority, WebSocketEvent event, T data) throws IOException {
        for (String username : authoritiesDao.getUsernamesByAuthority(authority.toString())) {
            emit(username, event, data);
        }
    }

}
