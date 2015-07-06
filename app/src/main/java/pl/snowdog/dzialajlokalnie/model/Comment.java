package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;


public class Comment {

    private int commentID;
    private int userID;
    private int parentId;
    private String parentType;
    private int solution;
    private String text;
    private Date createdAt;
    private Date updatedAt;
    private String commentHashtags;
    private String commentMentioned;

    public Comment(int commentID, int userID, int parentId, String parentType, int solution, String text, Date createdAt, Date updatedAt, String commentHashtags, String commentMentioned) {
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
}