package pl.snowdog.dzialajlokalnie.model;

import java.util.Date;


public class Alert_org {

    private int alertOrgID;
    private String name;
    private String description;
    private String url;
    private int cityID;
    private int districtID;

    public Alert_org(int alertOrgID, String name, String description, String url, int cityID, int districtID) {
        this.alertOrgID = alertOrgID;
        this.name = name;
        this.description = description;
        this.url = url;
        this.cityID = cityID;
        this.districtID = districtID;
    }
}