package pl.snowdog.dzialajlokalnie.model;

/**
 * Created by chomi3 on 2015-07-16.
 */
public class Point {
    public double x;
    public double y;

    public Point(double y, double x) { //X is lon, Y is lat
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}