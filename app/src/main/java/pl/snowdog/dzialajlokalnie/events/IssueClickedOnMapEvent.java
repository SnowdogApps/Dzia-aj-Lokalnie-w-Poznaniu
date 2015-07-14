package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 14.07.15.
 */
public class IssueClickedOnMapEvent {

    int id;

    public IssueClickedOnMapEvent(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IssueClickedOnMapEvent{" +
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
