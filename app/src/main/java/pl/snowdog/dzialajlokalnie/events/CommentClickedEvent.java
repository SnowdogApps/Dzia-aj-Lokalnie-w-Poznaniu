package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 16.07.15.
 */
public class CommentClickedEvent {
    private int id;

    public CommentClickedEvent(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CommentClickedEvent{" +
                "id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
