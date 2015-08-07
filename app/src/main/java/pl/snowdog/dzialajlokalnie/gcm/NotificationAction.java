package pl.snowdog.dzialajlokalnie.gcm;

/**
 * Created by chomi3 on 2015-07-29.
 */
public class NotificationAction {
    public static final String INTENT_ACTION = "action";
    public static final String ACTION_VALUE = "action_value";

    //ISSUES
    public static final int NEW_ISSUE_SURROUND = 10;
    public static final int EDIT_ISSUE = 11;
    public static final int DELETE_ISSUE = 12;

    //EVENTS
    public static final int NEW_EVENT_SURROUND = 20;
    public static final int NEW_EVENT_USER_PARTICIPATED = 21;
    public static final int EDIT_EVENT =22;
    public static final int DELETE_EVENT_USER_SAVED = 23;

    public static final int EVENT_REMINDER =24;

    //COMMENTS
    public static final int COMMENT_TO_ISSUE = 31;
    public static final int COMMENT_TO_EVENT = 32;

}
