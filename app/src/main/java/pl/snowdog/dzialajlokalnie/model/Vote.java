package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;


public class Vote {

    private int voteID;
    private int authorID;
    private int parentType;
    private int parentID;
    private int value;
    private Date createdAt;
    private Date updatedAt;

    public Vote(int voteID, int authorID, int parentType, int parentID, int value, Date createdAt, Date updatedAt) {
        this.voteID = voteID;
        this.authorID = authorID;
        this.parentType = parentType;
        this.parentID = parentID;
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}