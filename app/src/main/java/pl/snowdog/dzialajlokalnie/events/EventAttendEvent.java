package pl.snowdog.dzialajlokalnie.events;

import pl.snowdog.dzialajlokalnie.model.Event;
import pl.snowdog.dzialajlokalnie.model.ParticipateEvent;

/**
 * Created by bartek on 08.07.15.
 */
public class EventAttendEvent {

    private Event event;
    private ParticipateEvent.ParcitipateType parcitipateType;

    public EventAttendEvent(Event event, ParticipateEvent.ParcitipateType parcitipateType) {
        this.event = event;
        this.parcitipateType = parcitipateType;
    }

    @Override
    public String toString() {
        return "EventAttendEvent{" +
                "event=" + event +
                ", parcitipateType=" + parcitipateType +
                '}';
    }

    public Event getEvent() {
        return event;
    }

    public ParticipateEvent.ParcitipateType getParcitipateType() {
        return parcitipateType;
    }
}
