package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;


public class Notification {

    private int notificationID;
    private int userID;
    private String text;
    private int parentType;
    private int parentId;
    private Date createdAt;
    private Date updatedAt;
    private int notificationStatus;

    public Notification(int notificationID, int userID, String text, int parentType, int parentId, Date createdAt, Date updatedAt, int notificationStatus) {
        this.notificationID = notificationID;
        this.userID = userID;
        this.text = text;
        this.parentType = parentType;
        this.parentId = parentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.notificationStatus = notificationStatus;
    }
}