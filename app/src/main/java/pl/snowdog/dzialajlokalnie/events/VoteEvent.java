package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 07.07.15.
 */
public abstract class VoteEvent {

    public enum Vote {UP, DOWN}

    private int id;
    private Vote vote;

    public VoteEvent(int id, Vote vote) {
        this.id = id;
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "VoteEvent{" +
                "id=" + id +
                ", vote=" + vote +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
