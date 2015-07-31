package pl.snowdog.dzialajlokalnie.model;

/**
 * Created by bartek on 31.07.15.
 */
public class ParticipateEvent {

    public enum ParcitipateType {attending, maybe, declined, invited, banned}

    private int eventId;
    private int participateType;

    public ParticipateEvent(int eventId, int participateType) {
        this.eventId = eventId;
        this.participateType = participateType;
    }

    @Override
    public String toString() {
        return "ParticipateEvent{" +
                "eventId=" + eventId +
                ", participateType=" + participateType +
                '}';
    }

    public int getParticipateType() {
        return participateType;
    }

    public void setParticipateType(int participateType) {
        this.participateType = participateType;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
