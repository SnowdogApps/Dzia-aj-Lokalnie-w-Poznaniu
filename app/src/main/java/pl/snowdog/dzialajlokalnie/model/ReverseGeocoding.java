package pl.snowdog.dzialajlokalnie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chomi3 on 2015-07-16.
 */
public class ReverseGeocoding {
    List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public class Feature {

        @SerializedName("properties")
        private Property property;

        public Property getProperty() {
            return property;
        }

        public void setProperty(Property property) {
            this.property = property;
        }
    }

    public class Property {
        String typ;

        @SerializedName("wg_nazwiska")
        String nazwisko;

        @SerializedName("wg_imienia_wydruku")
        String imie;

        String nr;

        public Property() {
        }

        public String getTyp() {
            return typ;
        }

        public void setTyp(String typ) {
            this.typ = typ;
        }

        public String getNazwisko() {
            return nazwisko;
        }

        public void setNazwisko(String nazwisko) {
            this.nazwisko = nazwisko;
        }

        public String getImie() {
            return imie;
        }

        public void setImie(String imie) {
            this.imie = imie;
        }

        public String getNr() {
            return nr;
        }

        public void setNr(String nr) {
            this.nr = nr;
        }

        @Override
        public String toString() {
            return "Property{" +
                    "typ='" + typ + '\'' +
                    ", nazwisko='" + nazwisko + '\'' +
                    ", imie='" + imie + '\'' +
                    ", nr='" + nr + '\'' +
                    '}';
        }
    }
}
