package org.servicestation.resources.sokets;


public class EventKey {
    public String sessionId;
    public WebSocketEvent event;

    public EventKey(String sessionId, WebSocketEvent event) {
        this.sessionId = sessionId;
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventKey eventKey = (EventKey) o;

        if (sessionId != null ? !sessionId.equals(eventKey.sessionId) : eventKey.sessionId != null) return false;
        return event == eventKey.event;

    }

    @Override
    public int hashCode() {
        int result = sessionId != null ? sessionId.hashCode() : 0;
        result = 31 * result + (event != null ? event.hashCode() : 0);
        return result;
    }
}
