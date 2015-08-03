package pl.snowdog.dzialajlokalnie.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.Date;


@Table(name = "Users")
public class User extends Model implements Serializable {

    @Column(unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int userID;
    private int userLevel;

    @Column
    private String email;
    private String password;

    @Column
    private String description;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private int districtID;

    @Column
    private int enableEmailNotifications;

    @Column
    private int enablePushNotifications;

    @Column
    private String pushRegId;

    @Column
    private String avatarUri;
    private String apiKey;
    private Date lastLoginDate;
    private Date lastFailedLoginDate;
    private int failedLoginCount;

    @Column
    private int accountStatus;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    public User() {
        super();
    }

    public User(int userID, int userLevel, String email, String password, String description, String name, String surname, int districtID, int enableEmailNotifications, int enablePushNotifications, String pushRegId, String avatarUri, String apiKey, Date lastLoginDate, Date lastFailedLoginDate, int failedLoginCount, int accountStatus, Date createdAt, Date updatedAt) {
        super();
        this.userID = userID;
        this.userLevel = userLevel;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public int getEnableEmailNotifications() {
        return enableEmailNotifications;
    }

    public void setEnableEmailNotifications(int enableEmailNotifications) {
        this.enableEmailNotifications = enableEmailNotifications;
    }

    public int getEnablePushNotifications() {
        return enablePushNotifications;
    }

    public void setEnablePushNotifications(int enablePushNotifications) {
        this.enablePushNotifications = enablePushNotifications;
    }

    public String getPushRegId() {
        return pushRegId;
    }

    public void setPushRegId(String pushRegId) {
        this.pushRegId = pushRegId;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastFailedLoginDate() {
        return lastFailedLoginDate;
    }

    public void setLastFailedLoginDate(Date lastFailedLoginDate) {
        this.lastFailedLoginDate = lastFailedLoginDate;
    }

    public int getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(int failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
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

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userLevel=" + userLevel +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", districtID=" + districtID +
                ", enableEmailNotifications=" + enableEmailNotifications +
                ", enablePushNotifications=" + enablePushNotifications +
                ", pushRegId='" + pushRegId + '\'' +
                ", avatarUri='" + avatarUri + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", lastLoginDate=" + lastLoginDate +
                ", lastFailedLoginDate=" + lastFailedLoginDate +
                ", failedLoginCount=" + failedLoginCount +
                ", accountStatus=" + accountStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}