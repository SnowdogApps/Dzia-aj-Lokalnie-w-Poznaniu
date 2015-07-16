package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 07.07.15.
 */
public class IssueVoteEvent {

    public enum Vote {UP, DOWN}

    private int id;
    private Vote vote;

    public IssueVoteEvent(int id, Vote vote) {
        this.id = id;
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "IssueVoteEvent{" +
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
