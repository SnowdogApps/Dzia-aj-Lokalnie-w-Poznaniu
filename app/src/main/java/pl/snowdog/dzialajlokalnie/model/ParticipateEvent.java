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

    public ParticipateEvent(int eventId, ParcitipateType participateType) {
        this.eventId = eventId;

        int intPt;
        switch (participateType) {
            case attending:
                intPt = 1;
                break;
            case maybe:
                intPt = 2;
                break;
            case declined:
                intPt = 3;
                break;
            case invited:
                intPt = 4;
                break;
            case banned:
                intPt = 5;
                break;
            default:
                intPt = 0;
        }

        this.participateType = intPt;
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
