package york.mobiledev.nikoslintas.movieappcw.models;

import java.util.List;
import java.util.Objects;

public class SearchResponse {
    // Attributes
    public List<TrendingMovie> results;

    // Constructors

    public SearchResponse(List<TrendingMovie> results) {
        this.results = results;
    }

    // Methods
    public List<TrendingMovie> getResults() {
        return results;
    }

    public void setResults(List<TrendingMovie> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResponse that = (SearchResponse) o;
        return Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(results);
    }

    @Override
    public String toString() {
        return "SearchResponse{" +
                "results=" + results +
                '}';
    }
}
