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
}