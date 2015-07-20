package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 07.07.15.
 */
public class IssueVoteEvent extends VoteEvent {

    public IssueVoteEvent(int id, Vote vote) {
        super(id, vote);
    }
}
