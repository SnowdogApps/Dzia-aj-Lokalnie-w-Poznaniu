package pl.snowdog.dzialajlokalnie.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;

import java.util.Date;


@Table(name = "Issues")
public class Issue extends Model {

    private int issueID;
    private int parentID;
    private int userID;
    private String title;
    private String description;
    private int districtID;
    private double lon;
    private double lat;
    private String address;
    private String facebookUrl;
    private String issueUrl;
    private Date createdAt;
    private int issueStatus;
    private String categoryID;
    private String photoIssueUri;
    private int issueRating;
    private int commentsCount;
    private int votesCount;

    public Issue() {
        super();
    }

    public Issue(int issueID, int parentID, int userID, String title, String description, int districtID, double lon, double lat, String address, String facebookUrl, String issueUrl, Date createdAt, int issueStatus, String categoryID, String photoIssueUri, int issueRating, int commentsCount, int votesCount) {
        super();
        this.issueID = issueID;
        this.parentID = parentID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.districtID = districtID;
        this.lon = lon;
        this.lat = lat;
        this.address = address;
        this.facebookUrl = facebookUrl;
        this.issueUrl = issueUrl;
        this.createdAt = createdAt;
        this.issueStatus = issueStatus;
        this.categoryID = categoryID;
        this.photoIssueUri = photoIssueUri;
        this.issueRating = issueRating;
        this.commentsCount = commentsCount;
        this.votesCount = votesCount;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "issueID=" + issueID +
                ", parentID=" + parentID +
                ", userID=" + userID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", districtID=" + districtID +
                ", lon=" + lon +
                ", lat=" + lat +
                ", address='" + address + '\'' +
                ", facebookUrl='" + facebookUrl + '\'' +
                ", issueUrl='" + issueUrl + '\'' +
                ", createdAt=" + createdAt +
                ", issueStatus=" + issueStatus +
                ", categoryID='" + categoryID + '\'' +
                ", photoIssueUri='" + photoIssueUri + '\'' +
                ", issueRating=" + issueRating +
                ", commentsCount=" + commentsCount +
                ", votesCount=" + votesCount +
                '}';
    }

    public int getIssueID() {
        return issueID;
    }

    public void setIssueID(int issueID) {
        this.issueID = issueID;
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

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getIssueUrl() {
        return issueUrl;
    }

    public void setIssueUrl(String issueUrl) {
        this.issueUrl = issueUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(int issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getPhotoIssueUri() {
        return photoIssueUri;
    }

    public void setPhotoIssueUri(String photoIssueUri) {
        this.photoIssueUri = photoIssueUri;
    }

    public int getIssueRating() {
        return issueRating;
    }

    public void setIssueRating(int issueRating) {
        this.issueRating = issueRating;
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