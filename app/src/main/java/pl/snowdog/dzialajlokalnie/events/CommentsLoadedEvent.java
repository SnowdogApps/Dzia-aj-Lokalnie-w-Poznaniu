package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 24.07.15.
 */
public class CommentsLoadedEvent {
    private final int count;

    public CommentsLoadedEvent(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CommentsLoadedEvent{" +
                "count=" + count +
                '}';
    }

    public int getCount() {
        return count;
    }
}
