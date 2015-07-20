package pl.snowdog.dzialajlokalnie.events;

import pl.snowdog.dzialajlokalnie.model.Comment;

/**
 * Created by bartek on 17.07.15.
 */
public class NewCommentEvent {
    private final Comment comment;

    public NewCommentEvent(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "NewCommentEvent{" +
                "comment=" + comment +
                '}';
    }

    public Comment getComment() {
        return comment;
    }
}
