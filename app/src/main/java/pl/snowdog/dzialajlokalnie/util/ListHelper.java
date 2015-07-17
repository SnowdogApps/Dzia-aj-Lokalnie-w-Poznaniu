package pl.snowdog.dzialajlokalnie.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chomi3 on 2015-07-17.
 */
public class ListHelper {
    @NonNull
    public static  List<Integer> parseCategoryListFromString(String categoryID) {
        List<String> items = Arrays.asList(categoryID.split(","));
        List<Integer> ints = new ArrayList<>();
        for(String i : items) {
            try {
                ints.add(Integer.parseInt(i));
            }catch (NumberFormatException e) {
                ints.clear();
            }
        }
        return ints;
    }
}
