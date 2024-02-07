package york.mobiledev.nikoslintas.movieappcw.models;

import com.google.gson.annotations.SerializedName;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/* This is a POJO file for the TMDB API. */

public class SpecificMovie {
    // Attributes
    private boolean adult;
    @SerializedName("backdrop_path")
    private String backdrop;
    private int budget;
    private List<Genre> genres;
    private int id;
    @SerializedName("imdb_id")
    private String imdb_link;
    private String original_language;
    private String overview;
    private String poster_path;
    private List<ProductionCompany> production_companies;
    private String release_date;
    private int revenue;
    private int runtime;
    private String status;
    private String tagline;
    private String title;
    private double vote_average;
    private int vote_count;
    private String homepage;

    // Constructors
    public SpecificMovie(boolean adult, String backdrop, int budget, List<Genre> genres, int id, String imdb_link, String original_language, String overview, String poster_path, List<ProductionCompany> production_companies, String release_date, int revenue, int runtime, String status, String tagline, String title, double vote_average, int vote_count, String homepage) {
        this.adult = adult;
        this.backdrop = backdrop;
        this.budget = budget;
        this.genres = genres;
        this.id = id;
        this.imdb_link = imdb_link;
        this.original_language = original_language;
        this.overview = overview;
        this.poster_path = poster_path;
        this.production_companies = production_companies;
        this.release_date = release_date;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.homepage = homepage;

        transformSelfHyperlinks();
    }

    public SpecificMovie() {
        this.adult = false;
        this.backdrop = "";
        this.budget = 0;
        this.genres = null;
        this.id = 0;
        this.imdb_link = "";
        this.original_language = "";
        this.overview = "";
        this.poster_path = "";
        this.production_companies = null;
        this.release_date = "";
        this.revenue = 0;
        this.runtime = 0;
        this.status = "";
        this.tagline = "";
        this.title = "";
        this.vote_average = 0.0;
        this.vote_count = 0;
        this.homepage = "";
    }

    // Methods
    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = MessageFormat.format("https://image.tmdb.org/t/p/w780/{0}", backdrop);
        ;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdb_link() {
        return imdb_link;
    }

    public void setImdb_link(String imdb_link) {
        this.imdb_link = MessageFormat.format("https://www.imdb.com/title/{0}/", imdb_link);
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = MessageFormat.format("https://image.tmdb.org/t/p/w500/{0}", poster_path);
        ;
    }

    public List<ProductionCompany> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<ProductionCompany> production_companies) {
        this.production_companies = production_companies;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificMovie that = (SpecificMovie) o;
        return adult == that.adult && budget == that.budget && id == that.id && revenue == that.revenue && runtime == that.runtime && Double.compare(that.vote_average, vote_average) == 0 && vote_count == that.vote_count && Objects.equals(backdrop, that.backdrop) && Objects.equals(genres, that.genres) && Objects.equals(imdb_link, that.imdb_link) && Objects.equals(original_language, that.original_language) && Objects.equals(overview, that.overview) && Objects.equals(poster_path, that.poster_path) && Objects.equals(production_companies, that.production_companies) && Objects.equals(release_date, that.release_date) && Objects.equals(status, that.status) && Objects.equals(tagline, that.tagline) && Objects.equals(title, that.title) && Objects.equals(homepage, that.homepage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adult, backdrop, budget, genres, id, imdb_link, original_language, overview, poster_path, production_companies, release_date, revenue, runtime, status, tagline, title, vote_average, vote_count, homepage);
    }

    @Override
    public String toString() {
        return "SpecificMovie{" +
                "adult=" + adult +
                ", backdrop='" + backdrop + '\'' +
                ", budget=" + budget +
                ", genres=" + genres +
                ", id=" + id +
                ", imdb_link='" + imdb_link + '\'' +
                ", original_language='" + original_language + '\'' +
                ", overview='" + overview + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", productionCompanies=" + production_companies +
                ", release_date='" + release_date + '\'' +
                ", revenue=" + revenue +
                ", runtime=" + runtime +
                ", status='" + status + '\'' +
                ", tagline='" + tagline + '\'' +
                ", title='" + title + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", homepage='" + homepage + '\'' +
                '}';
    }

    // Use it to transform the links for images and imdb into working links.
    public void transformSelfHyperlinks() {
        setImdb_link(imdb_link);
        setBackdrop(backdrop);
        setPoster_path(poster_path);
    }
}
