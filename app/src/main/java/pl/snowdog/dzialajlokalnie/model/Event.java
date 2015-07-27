package pl.snowdog.dzialajlokalnie.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import pl.snowdog.dzialajlokalnie.util.ListHelper;


@Table(name = "Events")
public class Event extends Model implements Serializable {

    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int eventID;

    @Column
    private int parentID;

    @Column
    private int userID;

    @Column
    private String authorName;

    @Column
    private String authorAvatar;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private int districtID;

    @Column
    private double lon;

    @Column
    private double lat;

    @Column
    private String address;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private String facebookUrl;

    @Column
    private String eventUrl;

    @Column
    private Date createdAt;

    @Column
    private int eventStatus;

    @Column
    private String categoryID;

    @Column
    private String photoEventUri;

    @Column
    private int commentsCount;

    @Column
    private int votesCount;

    @Column
    private int invitedCount;

    @Column
    private int attendingCount;

    @Column
    private int maybeCount;

    @Column
    private int declinedCount;

    @Column
    private int userStatus;

    private String categoriesText;
    private String districtName;

    @Column
    private List<Integer> categoriesList;


    public Event() {
        super();
    }

    public Event(int eventID, int parentID, int userID, String title, String description, int districtID, double lon, double lat, String address, Date startDate, Date endDate, String facebookUrl, String eventUrl, Date createdAt, int eventStatus, String categoryID, String photoEventUri, int commentsCount, int votesCount, int invitedCount, int attendingCount, int maybeCount, int declinedCount, int userStatus) {
        super();
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
        this.invitedCount = invitedCount;
        this.attendingCount = attendingCount;
        this.maybeCount = maybeCount;
        this.declinedCount = declinedCount;
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID=" + eventID +
                ", parentID=" + parentID +
                ", userID=" + userID +
                ", authorName='" + authorName + '\'' +
                ", authorAvatar='" + authorAvatar + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", districtID=" + districtID +
                ", lon=" + lon +
                ", lat=" + lat +
                ", address='" + address + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", facebookUrl='" + facebookUrl + '\'' +
                ", eventUrl='" + eventUrl + '\'' +
                ", createdAt=" + createdAt +
                ", eventStatus=" + eventStatus +
                ", categoryID='" + categoryID + '\'' +
                ", photoEventUri='" + photoEventUri + '\'' +
                ", commentsCount=" + commentsCount +
                ", votesCount=" + votesCount +
                ", invitedCount=" + invitedCount +
                ", attendingCount=" + attendingCount +
                ", maybeCount=" + maybeCount +
                ", declinedCount=" + declinedCount +
                ", userStatus=" + userStatus +
                ", categoriesText='" + categoriesText + '\'' +
                ", districtName='" + districtName + '\'' +
                ", categoriesList=" + categoriesList +
                '}';
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public List<Integer> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Integer> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public String getCategoriesText() {
        return categoriesText;
    }

    public void setCategoriesText(String categoriesText) {
        this.categoriesText = categoriesText;
    }

    public int getInvitedCount() {
        return invitedCount;
    }

    public void setInvitedCount(int invitedCount) {
        this.invitedCount = invitedCount;
    }

    public int getAttendingCount() {
        return attendingCount;
    }

    public void setAttendingCount(int attendingCount) {
        this.attendingCount = attendingCount;
    }

    public int getMaybeCount() {
        return maybeCount;
    }

    public void setMaybeCount(int maybeCount) {
        this.maybeCount = maybeCount;
    }

    public int getDeclinedCount() {
        return declinedCount;
    }

    public void setDeclinedCount(int declinedCount) {
        this.declinedCount = declinedCount;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
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

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
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

    public void parseCategoriesList() {
        categoriesList = ListHelper.parseCategoryListFromString(categoryID);
    }

    public List<Integer> getCategoryIdsList() {
        return categoriesList;
    }

    public class EventWrapper {
        private int eventID;

        public int getEventID() {
            return eventID;
        }

        public void setEventID(int eventID) {
            this.eventID = eventID;
        }

        /*private Event event;

        public Event getEvent() {
            return event;
        }

        public void setEvent(Event event) {
            this.event = event;
        }*/
    }
}