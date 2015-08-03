package pl.snowdog.dzialajlokalnie.events;

import java.io.Serializable;
import java.util.List;

import pl.snowdog.dzialajlokalnie.model.DateWrapper;

/**
 * Created by chomi3 on 2015-07-15.
 */
public class CreateNewObjectEvent implements Serializable {
    public enum Type{title, date, location, image, category, details, facebook};
    Type type;
    String title;
    String description;
    int districtID;
    double lat;
    double lon;
    DateWrapper startDate;
    DateWrapper endDate;
    String address;
    List<Integer> categoryIDs;
    String image;
    String name;
    String surname;
    String email;
    String password;

    public static class Builder {
        Type type;
        String title;
        String description;
        DateWrapper startDate;
        DateWrapper endDate;
        int districtID;
        double lat;
        double lon;
        String address;
        List<Integer> categoryIDs;
        String image;
        String name;
        String surname;
        String email;
        String password;

        public Builder type(Type type) {this.type = type; return this;}
        public Builder title(String title) {this.title = title; return this;}
        public Builder description(String description) {this.description = description; return this;}
        public Builder startDate(DateWrapper startDate) {this.startDate = startDate; return this;}
        public Builder endDate(DateWrapper endDate) {this.endDate = endDate; return this;}
        public Builder districtID(int districtID) {this.districtID = districtID; return this;}
        public Builder lat(double lat) {this.lat = lat; return this;}
        public Builder lon(double lon) {this.lon = lon; return this;}
        public Builder address(String address) {this.address = address; return this;}
        public Builder categoryIDs(List<Integer> categoryIDs) {this.categoryIDs = categoryIDs; return this;}
        public Builder image(String image) {this.image = image; return this;}
        public Builder name(String name) {this.name = name; return this;}
        public Builder surname(String surname) {this.surname = surname; return this;}
        public Builder email(String email) {this.email = email; return this;}
        public Builder password(String password) {this.password = password; return this;}

        //return fully build object
        public CreateNewObjectEvent build() {
            return new CreateNewObjectEvent(this);
        }


    }

    //private constructor to enforce object creation through builder
    private CreateNewObjectEvent(Builder builder) {
        this.type = builder.type;
        this.title = builder.title;
        this.description = builder.description;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.districtID = builder.districtID;
        this.lat = builder.lat;
        this.lon = builder.lon;
        this.address = builder.address;
        this.categoryIDs = builder.categoryIDs;
        this.image = builder.image;
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
        this.password = builder.password;

    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public DateWrapper getStartDate() {
        return startDate;
    }

    public void setStartDate(DateWrapper startDate) {
        this.startDate = startDate;
    }

    public DateWrapper getEndDate() {
        return endDate;
    }

    public void setEndDate(DateWrapper endDate) {
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

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Integer> getCategoryIDs() {
        return categoryIDs;
    }

    public void setCategoryIDs(List<Integer> categoryIDs) {
        this.categoryIDs = categoryIDs;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
