package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;


public class User {

    private int userID;
    private int userLevel;
    private String username;
    private String email;
    private String password;
    private String description;
    private String name;
    private String surname;
    private int districtID;
    private int enableEmailNotifications;
    private int enablePushNotifications;
    private String pushRegId;
    private String avatarUri;
    private String apiKey;
    private Date lastLoginDate;
    private Date lastFailedLoginDate;
    private int failedLoginCount;
    private int accountStatus;
    private Date createdAt;
    private Date updatedAt;

    public User(int userID, int userLevel, String username, String email, String password, String description, String name, String surname, int districtID, int enableEmailNotifications, int enablePushNotifications, String pushRegId, String avatarUri, String apiKey, Date lastLoginDate, Date lastFailedLoginDate, int failedLoginCount, int accountStatus, Date createdAt, Date updatedAt) {
        this.userID = userID;
        this.userLevel = userLevel;
        this.username = username;
        this.email = email;
        this.password = password;
        this.description = description;
        this.name = name;
        this.surname = surname;
        this.districtID = districtID;
        this.enableEmailNotifications = enableEmailNotifications;
        this.enablePushNotifications = enablePushNotifications;
        this.pushRegId = pushRegId;
        this.avatarUri = avatarUri;
        this.apiKey = apiKey;
        this.lastLoginDate = lastLoginDate;
        this.lastFailedLoginDate = lastFailedLoginDate;
        this.failedLoginCount = failedLoginCount;
        this.accountStatus = accountStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}