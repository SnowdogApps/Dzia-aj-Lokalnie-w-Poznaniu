package pl.snowdog.dzialajlokalnie.model;

import java.util.List;

/**
 * Created by bartek on 10.07.15.
 */
public class Filter {

    private District district;
    private List<Category> categories;

    public Filter(District district, List<Category> categories) {
        this.district = district;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "district=" + district +
                ", categories=" + categories +
                '}';
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
