package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;


public class Session {

    private String ssid;
    private int userID;
    private int deviceType;
    private String ip;
    private String ua;
    private Date createdAt;
    private Date expiredAt;

    public Session(String ssid, int userID, int deviceType, String ip, String ua, Date createdAt, Date expiredAt) {
        this.ssid = ssid;
        this.userID = userID;
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
}