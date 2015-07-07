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
    private int commentsCount;
    private int votesCount;

    public Event() {
    }

    public Event(int eventID, int parentID, int userID, String title, String description, int districtID, double lon, double lat, String address, Date startDate, Date endDate, String facebookUrl, String eventUrl, Date createdAt, int eventStatus, int categoryID, String photoEventUri, int commentsCount, int votesCount) {
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
        this.commentsCount = commentsCount;
        this.votesCount = votesCount;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(int eventStatus) {
        this.eventStatus = eventStatus;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getPhotoEventUri() {
        return photoEventUri;
    }

    public void setPhotoEventUri(String photoEventUri) {
        this.photoEventUri = photoEventUri;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(int votesCount) {
        this.votesCount = votesCount;
    }
}