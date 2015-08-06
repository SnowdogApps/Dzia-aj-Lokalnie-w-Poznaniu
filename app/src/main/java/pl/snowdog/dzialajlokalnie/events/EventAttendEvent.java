package pl.snowdog.dzialajlokalnie.events;

import pl.snowdog.dzialajlokalnie.model.Event;

/**
 * Created by bartek on 08.07.15.
 */
public class EventAttendEvent {

    private Event event;

    public EventAttendEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "EventAttendEvent{" +
                "event=" + event +
                '}';
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
