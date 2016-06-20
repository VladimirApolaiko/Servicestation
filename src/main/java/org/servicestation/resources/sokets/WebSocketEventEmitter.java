package org.servicestation.resources.sokets;

import org.apache.log4j.spi.LoggerFactory;
import org.servicestation.dao.IAuthoritiesDao;
import org.servicestation.dao.IUserDao;
import org.servicestation.resources.managers.Authority;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WebSocketEventEmitter implements IWebSocketEventEmitter {


    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(WebSocketEventEmitter.class);

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
    public void unregisterEventHandler(String username, String sessionId, WebSocketEvent event) {
        HashMap<EventKey, WebSocketEventHandler> usersEventHandlers = eventHandlers.get(username);
        usersEventHandlers.remove(new EventKey(sessionId, event));
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

    public void unregisterAllSessionHandlers(String sessionId) {
        Optional<Map.Entry<String, HashMap<EventKey, WebSocketEventHandler>>> sessionEventsOptional = eventHandlers.entrySet().stream()
                .filter(events ->
                        events.getValue().entrySet().stream()
                                .filter(e -> e.getKey().sessionId.equals(sessionId))
                                .findFirst().isPresent()).findFirst();

        if(sessionEventsOptional.isPresent()) {
            Map.Entry<String, HashMap<EventKey, WebSocketEventHandler>> sessionEvents = sessionEventsOptional.get();
            String username = sessionEvents.getKey();

            Stream<EventKey> eventKeys = sessionEvents.getValue().keySet().stream().map(eventKey -> new EventKey(eventKey.sessionId, eventKey.event));
            eventKeys.forEach(eventKey -> unregisterEventHandler(username, sessionId, eventKey.event));
        }else {
            LOGGER.info("Events for sessions id {} not found", sessionId);
        }
    }

    public <T> void emitForAuthorities(Authority authority, WebSocketEvent event, T data) throws IOException {
        for (String username : authoritiesDao.getUsernamesByAuthority(authority.toString())) {
            emit(username, event, data);
        }
    }

}
