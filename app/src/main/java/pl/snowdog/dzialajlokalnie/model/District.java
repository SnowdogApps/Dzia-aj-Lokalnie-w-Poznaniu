package pl.snowdog.dzialajlokalnie.model;

public class District {

    private int districtID;
    private String name;
    private String description;
    private int cityID;

    public District(int districtID, String name, String description, int cityID) {
        this.districtID = districtID;
        this.name = name;
        this.description = description;
        this.cityID = cityID;
    }
}