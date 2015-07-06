package pl.snowdog.dzialajlokalnie.model;

public class Event_has_user {

    private int eventID;
    private int userID;
    private String participateType;

    public Event_has_user(int eventID, int userID, String participateType) {
        this.eventID = eventID;
        this.userID = userID;
        this.participateType = participateType;
    }
}