package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 14.07.15.
 */
public abstract class ClickedEvent {

    int id;

    public ClickedEvent(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClickedEvent{" +
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
