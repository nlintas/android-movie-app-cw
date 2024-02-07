package york.mobiledev.nikoslintas.movieappcw.models;

import com.google.gson.annotations.SerializedName;

import java.text.MessageFormat;
import java.util.Objects;

/* This is a POJO file for the TMDB API. */

public class ProductionCompany {
    // Attributes
    private String name;
    @SerializedName("origin_country")
    private String country;

    // Constructors
    public ProductionCompany(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public ProductionCompany() {
        this.name = "";
        this.country = "";
    }

    // Methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionCompany that = (ProductionCompany) o;
        return Objects.equals(name, that.name) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }

    @Override
    public String toString() {
        return name + " from " + country;
    }
}
