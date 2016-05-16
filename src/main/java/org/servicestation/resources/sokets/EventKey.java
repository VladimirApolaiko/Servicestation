package org.servicestation.resources.sokets;


public class EventKey {
    public String username;
    public WebSocketEvent event;

    public EventKey(String username, WebSocketEvent event) {
        this.username = username;
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventKey eventKey = (EventKey) o;

        if (username != null ? !username.equals(eventKey.username) : eventKey.username != null) return false;
        return event == eventKey.event;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (event != null ? event.hashCode() : 0);
        return result;
    }
}
