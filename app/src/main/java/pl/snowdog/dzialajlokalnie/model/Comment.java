package pl.snowdog.dzialajlokalnie.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;


@Table(name = "Comments")
public class Comment {


    @Column
    private int commentID;

    @Column
    private int userID;

    @Column
    private int parentId;

    @Column
    private String parentType;

    @Column
    private int solution;

    @Column
    private String text;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private String commentHashtags;

    @Column
    private String commentMentioned;

    public Comment() {
        super();
    }

    public Comment(int commentID, int userID, int parentId, String parentType, int solution, String text, Date createdAt, Date updatedAt, String commentHashtags, String commentMentioned) {
        super();
        this.commentID = commentID;
        this.userID = userID;
        this.parentId = parentId;
        this.parentType = parentType;
        this.solution = solution;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.commentHashtags = commentHashtags;
        this.commentMentioned = commentMentioned;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentID=" + commentID +
                ", userID=" + userID +
                ", parentId=" + parentId +
                ", parentType='" + parentType + '\'' +
                ", solution=" + solution +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", commentHashtags='" + commentHashtags + '\'' +
                ", commentMentioned='" + commentMentioned + '\'' +
                '}';
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public int getSolution() {
        return solution;
    }

    public void setSolution(int solution) {
        this.solution = solution;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getCommentHashtags() {
        return commentHashtags;
    }

    public void setCommentHashtags(String commentHashtags) {
        this.commentHashtags = commentHashtags;
    }

    public String getCommentMentioned() {
        return commentMentioned;
    }

    public void setCommentMentioned(String commentMentioned) {
        this.commentMentioned = commentMentioned;
    }
}