package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 14.07.15.
 */
public class EventClickedOnMapEvent {

    int id;

    public EventClickedOnMapEvent(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EventClickedOnMapEvent{" +
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
