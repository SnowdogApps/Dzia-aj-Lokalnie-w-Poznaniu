package pl.snowdog.dzialajlokalnie.model;

import java.util.List;

import pl.snowdog.dzialajlokalnie.util.PolygonUtil;

/**
 * Created by chomi3 on 2015-07-16.
 */
public class Polygon {
    List<Point> pointsList;

    public Polygon() {

    }

    public List<Point> getPoints() {
        return pointsList;
    }

    public void setPointsList(List<Point> pointsList) {
        this.pointsList = pointsList;
    }
}