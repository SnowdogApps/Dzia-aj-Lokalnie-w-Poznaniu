package pl.snowdog.dzialajlokalnie.events;

import pl.snowdog.dzialajlokalnie.model.Comment;

/**
 * Created by bartek on 16.07.15.
 */
public class CommentClickedEvent extends ClickedEvent {


    private final Comment comment;

    public CommentClickedEvent(Comment comment) {
        super(comment.getCommentID());

        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentClickedEvent{" +
                "comment=" + comment +
                '}';
    }

    public Comment getComment() {
        return comment;
    }
}
