package pl.snowdog.dzialajlokalnie.model;

import com.activeandroid.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.snowdog.dzialajlokalnie.util.PolygonUtil;

/**
 * Created by chomi3 on 2015-07-16.
 */
public class Polygon extends Model implements Serializable {
    List<Point> pointsList;

    public Polygon() {

    }

    public Polygon(String coordinates) {
        if(coordinates == null || coordinates.length() == 0) return;
        pointsList = new ArrayList<>();
        parseCoordinatesToPointsList(coordinates);
    }

    private void parseCoordinatesToPointsList(String coordinates) {
        String [] latLngs = coordinates.split(",");
        for(int i = 0; i < latLngs.length; i++) {
            String[] points = latLngs[i].split(" ");
            try {
                pointsList.add(new Point(Double.parseDouble(points[0]), Double.parseDouble(points[1])));
            } catch (NumberFormatException e) {
                pointsList.clear();
            }
        }
    }

    public List<Point> getPoints() {
        return pointsList;
    }

    public void setPointsList(List<Point> pointsList) {
        this.pointsList = pointsList;
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "pointsList=" + pointsList +
                '}';
    }
}