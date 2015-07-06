package pl.snowdog.dzialajlokalnie.model;

public class District {

    private int districtID;
    private String name;
    private String description;
    private double lon;
    private double lat;
    private int cityID;

    public District(int districtID, String name, String description, double lon, double lat, int cityID) {
        this.districtID = districtID;
        this.name = name;
        this.description = description;
        this.lon = lon;
        this.lat = lat;
        this.cityID = cityID;
    }
}