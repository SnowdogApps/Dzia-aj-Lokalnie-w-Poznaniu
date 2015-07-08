package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;


public class Vote {

    public enum ParentType {issues, comments};

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

    @Override
    public String toString() {
        return "Vote{" +
                "voteID=" + voteID +
                ", authorID=" + authorID +
                ", parentType=" + parentType +
                ", parentID=" + parentID +
                ", value=" + value +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public int getVoteID() {
        return voteID;
    }

    public void setVoteID(int voteID) {
        this.voteID = voteID;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public int getParentType() {
        return parentType;
    }

    public void setParentType(int parentType) {
        this.parentType = parentType;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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