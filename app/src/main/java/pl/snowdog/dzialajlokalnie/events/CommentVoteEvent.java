package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 07.07.15.
 */
public class CommentVoteEvent extends VoteEvent {

    public CommentVoteEvent(int id, Vote vote) {
        super(id, vote);
    }
}
