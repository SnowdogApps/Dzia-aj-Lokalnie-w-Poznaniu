package pl.snowdog.dzialajlokalnie.events;

/**
 * Created by chomi3 on 2015-07-22.
 */
public class ObjectAddedEvent {
    public enum Type{issue, event, user};
    private Type added;

    public ObjectAddedEvent(Type added) {
        this.added = added;
    }

    public Type getAdded() {
        return added;
    }

    public void setAdded(Type added) {
        this.added = added;
    }
}
