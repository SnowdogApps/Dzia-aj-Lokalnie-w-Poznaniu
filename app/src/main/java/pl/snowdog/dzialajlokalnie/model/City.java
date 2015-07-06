package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;


public class City {

    private int cityID;
    private String name;
    private String description;
    private double lon;
    private double lat;
    private Date createdAt;
    private Date updatedAt;

    public City(int cityID, String name, String description, double lon, double lat, Date createdAt, Date updatedAt) {
        this.cityID = cityID;
        this.name = name;
        this.description = description;
        this.lon = lon;
        this.lat = lat;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}