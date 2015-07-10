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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}