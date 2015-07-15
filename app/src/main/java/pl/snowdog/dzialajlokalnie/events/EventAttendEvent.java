package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 08.07.15.
 */
public class EventAttendEvent {

    private int eventId;

    public EventAttendEvent(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "EventAttendEvent{" +
                "eventId=" + eventId +
                '}';
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
