package pl.snowdog.dzialajlokalnie.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;


@Table(name = "Sessions")
public class Session extends Model {
    public static final int FACEBOOK_SESSION = 3;

    @Column(name = "Ssid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String ssid;

    @Column(name = "apikey", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String apikey;

    @Column
    private int userID;

    @Column
    private int isSessionAuthByAPIkey;

    private int deviceType;
    private String ip;
    private String ua;
    private Date createdAt;
    private Date expiredAt;

    public Session() {
        super();
    }

    public Session(String ssid, String apikey, int userID, int isSessionAuthByAPIkey, int deviceType, String ip, String ua, Date createdAt, Date expiredAt) {
        this.ssid = ssid;
        this.apikey = apikey;
        this.userID = userID;
        this.isSessionAuthByAPIkey = isSessionAuthByAPIkey;
        this.deviceType = deviceType;
        this.ip = ip;
        this.ua = ua;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    @Override
    public String toString() {
        return "Session{" +
                "ssid='" + ssid + '\'' +
                ", userID=" + userID +
                ", deviceType=" + deviceType +
                ", ip='" + ip + '\'' +
                ", ua='" + ua + '\'' +
                ", createdAt=" + createdAt +
                ", expiredAt=" + expiredAt +
                '}';
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getApiKey() {
        return apikey;
    }

    public void setApiKey(String apiKey) {
        this.apikey = apiKey;
    }

    public int getIsSessionAuthByAPIkey() {
        return isSessionAuthByAPIkey;
    }

    public void setIsSessionAuthByAPIkey(int isSessionAuthByAPIkey) {
        this.isSessionAuthByAPIkey = isSessionAuthByAPIkey;
    }

    public boolean isFacebookSession() {
        return isSessionAuthByAPIkey == FACEBOOK_SESSION ? true : false;
    }
}