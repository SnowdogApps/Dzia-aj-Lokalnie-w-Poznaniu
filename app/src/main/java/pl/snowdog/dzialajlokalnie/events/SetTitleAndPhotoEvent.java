package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by bartek on 16.07.15.
 */
public class SetTitleAndPhotoEvent {

    String title;
    String photoUrl;

    public SetTitleAndPhotoEvent(String title, String photoUrl) {
        this.title = title;
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "SetTitleAndPhotoEvent{" +
                "title='" + title + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
