package pl.snowdog.dzialajlokalnie.util;


import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Vector;

import pl.snowdog.dzialajlokalnie.model.Point;
import pl.snowdog.dzialajlokalnie.model.Polygon;

/**
 * Created by chomi3 on 2015-07-16.
 */
public class PolygonUtil {
    public static boolean isPointInPolygon(LatLng point, Polygon polygon) {
        List<Point> points = polygon.getPoints();
        int i, j, nvert = points.size();
        boolean c = false;

        for(i = 0, j = nvert - 1; i < nvert; j = i++) {
            if( ( (points.get(i).y >= point.latitude ) != (points.get(j).y >= point.latitude) ) &&
                    (point.longitude <= (points.get(j).x - points.get(i).x) * (point.latitude - points.get(i).y) / (points.get(j).y - points.get(i).y) + points.get(i).x)
                    )
                c = !c;
        }

        return c;
    }
}
