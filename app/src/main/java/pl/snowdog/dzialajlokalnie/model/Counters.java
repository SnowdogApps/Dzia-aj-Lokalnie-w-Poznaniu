package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;


public class Counters {

    private int counterID;
    private int parentType;
    private int parentID;
    private int commentsCount;
    private int votesCount;
    private int viewsCount;

    public Counters(int counterID, int parentType, int parentID, int commentsCount, int votesCount, int viewsCount) {
        this.counterID = counterID;
        this.parentType = parentType;
        this.parentID = parentID;
        this.commentsCount = commentsCount;
        this.votesCount = votesCount;
        this.viewsCount = viewsCount;
    }
}