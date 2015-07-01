package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;


public class City {

    private int cityID;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    public City(int cityID, String name, String description, Date createdAt, Date updatedAt) {
        this.cityID = cityID;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}