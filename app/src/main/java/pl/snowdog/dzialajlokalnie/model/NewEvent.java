package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;
import java.util.List;

/**
 * Created by chomi3 on 2015-07-16.
 */
public class NewEvent {
    private String title;
    private String description;
    private String address;
    private Date startDate;
    private Date endDate;
    private int districtID;
    private String location;

    private List<Integer> categoryID;
    private String photoEvent;

    public NewEvent() {

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Integer> getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(List<Integer> categoryID) {
        this.categoryID = categoryID;
    }

    public String getPhotoEvent() {
        return photoEvent;
    }

    public void setPhotoEvent(String photoEvent) {
        this.photoEvent = photoEvent;
    }
}
