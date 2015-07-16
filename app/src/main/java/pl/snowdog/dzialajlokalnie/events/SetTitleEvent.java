package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 16.07.15.
 */
public class SetTitleEvent {

    String title;

    public SetTitleEvent(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SetTitleEvent{" +
                "title='" + title + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
