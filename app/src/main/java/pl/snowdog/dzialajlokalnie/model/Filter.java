package pl.snowdog.dzialajlokalnie.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bartek on 10.07.15.
 *
 * Contains filter and sort settings for api
 */
public class Filter {

    public enum Sort {popular, newest, top}

    private District district;
    private List<Category> categories;
    private Sort sort;

    public Filter() {
        this.sort = Sort.popular;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "district=" + district +
                ", categories=" + categories +
                '}';
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public String getSort() {
        switch (sort) {
            case newest:
                return "issue.issueID DESC";
            case top:
                return "issue.issueRating DESC";
            default:
                return null;
        }
    }

    public String getDistrictFilter() {
        if (district == null) {
            return null;
        }

        return String.valueOf(district.getDistrictID());
    }

    public String getCategoriesFilter() {
        if (getCategories().size() == 0) {
            return null;
        }

        String filter = "";

        for (int i = 0; i < categories.size(); i++) {
            if (i != 0) {
                filter += ",";
            }
            filter += categories.get(i).getCategoryID();
        }

        return filter;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<Category> getCategories() {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
