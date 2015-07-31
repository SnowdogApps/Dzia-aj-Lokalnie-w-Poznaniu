package pl.snowdog.dzialajlokalnie.model;

/**
 * Created by bartek on 31.07.15.
 */
public class ParticipateEvent {

    private int participateType;

    public ParticipateEvent(int participateType) {
        this.participateType = participateType;
    }

    @Override
    public String toString() {
        return "ParticipateEvent{" +
                "participateType=" + participateType +
                '}';
    }

    public int getParticipateType() {
        return participateType;
    }

    public void setParticipateType(int participateType) {
        this.participateType = participateType;
    }
}
