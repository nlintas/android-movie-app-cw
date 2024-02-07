package york.mobiledev.nikoslintas.movieappcw.models;

import java.util.Objects;

/* This is a POJO file for the TMDB API. */

public class Genre {
    // Attributes
    String name;

    // Constructors
    public Genre(String name) {
        this.name = name;
    }

    public Genre() {
        this.name = "";
    }

    // Methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
