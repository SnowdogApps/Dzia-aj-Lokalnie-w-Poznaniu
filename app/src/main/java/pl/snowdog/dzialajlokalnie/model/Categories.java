package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;


public class Categories {

    private int categoryID;
    private String name;
    private int categoryType;
    private Date createdAt;
    private Date updatedAt;

    public Categories(int categoryID, String name, int categoryType, Date createdAt, Date updatedAt) {
        this.categoryID = categoryID;
        this.name = name;
        this.categoryType = categoryType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}