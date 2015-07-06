package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;


public class Event {

    private int eventID;
    private int parentID;
    private int userID;
    private String title;
    private String description;
    private int districtID;
    private double lon;
    private double lat;
    private String address;
    private Date startDate;
    private Date endDate;
    private String facebookUrl;
    private String eventUrl;
    private Date createdAt;
    private int eventStatus;
    private int categoryID;
    private String photoEventUri;

    public Event(int eventID, int parentID, int userID, String title, String description, int districtID, double lon, double lat, String address, Date startDate, Date endDate, String facebookUrl, String eventUrl, Date createdAt, int eventStatus, int categoryID, String photoEventUri) {
        this.eventID = eventID;
        this.parentID = parentID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.districtID = districtID;
        this.lon = lon;
        this.lat = lat;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.facebookUrl = facebookUrl;
        this.eventUrl = eventUrl;
        this.createdAt = createdAt;
        this.eventStatus = eventStatus;
        this.categoryID = categoryID;
        this.photoEventUri = photoEventUri;
    }
}