package york.mobiledev.nikoslintas.movieappcw.models;

import com.google.gson.annotations.SerializedName;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

public class TrendingMovie {
    // Attributes
    @SerializedName("poster_path")
    String poster;
    int id;
    String title;

    // Constructors
    public TrendingMovie(String poster, int id, String title) {
        this.poster = poster;
        this.id = id;
        this.title = title;
    }

    public TrendingMovie() {
        this.poster = "";
        this.id = 0;
        this.title = "";
    }

    // Methods
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = MessageFormat.format("https://image.tmdb.org/t/p/w500/{0}", poster);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void transformSelfHyperlinks() {
        setPoster(poster);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrendingMovie that = (TrendingMovie) o;
        return id == that.id && Objects.equals(poster, that.poster) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(poster, id, title);
    }

    @Override
    public String toString() {
        return "TrendingMovie{" +
                "poster='" + poster + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
