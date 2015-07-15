package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 07.07.15.
 */
public class IssueRateEvent {

    public enum Vote {UP, DOWN};

    private int issueId;
    private Vote vote;

    public IssueRateEvent(int issueId, Vote vote) {
        this.issueId = issueId;
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "IssueRateEvent{" +
                "issueId=" + issueId +
                ", vote=" + vote +
                '}';
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
