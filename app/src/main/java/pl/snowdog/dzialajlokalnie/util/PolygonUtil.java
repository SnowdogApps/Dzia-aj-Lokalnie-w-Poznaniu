package pl.snowdog.dzialajlokalnie.util;


import android.content.Context;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;
import java.util.Vector;

import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.model.District;
import pl.snowdog.dzialajlokalnie.model.Point;
import pl.snowdog.dzialajlokalnie.model.Polygon;

/**
 * Created by chomi3 on 2015-07-16.
 */
public class PolygonUtil {
    private static final float STROKE_WIDTH = 3f;

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

    public static void createDistrictShapeOnMap(GoogleMap map, District district, Context context) {
        if(context == null || map == null) return;
        // Instantiates a new Polygon object and adds points to define a rectangle
        PolygonOptions rectOptions = new PolygonOptions();
        for (Point p : district.getPolygon().getPoints()) {
            rectOptions.add(new LatLng(p.getY(), p.getX()));
        }
        rectOptions.strokeColor(context.getResources().getColor(R.color.map_shape_stroke))
                //.fillColor(context.getResources().getColor(R.color.map_shape_fill))
                .strokeWidth(STROKE_WIDTH);

        map.addPolygon(rectOptions);
    }
}
