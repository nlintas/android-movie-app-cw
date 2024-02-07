package york.mobiledev.nikoslintas.movieappcw.models;

import java.util.List;
import java.util.Objects;

public class TrendingResponse {
    // Attributes
    int page;
    public List<TrendingMovie> results;
    int total_pages;
    int total_results;

    // Constructors
    public TrendingResponse(int page, List<TrendingMovie> results, int total_pages, int total_results) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public TrendingResponse() {
        this.page = 0;
        this.results = null;
        this.total_pages = 0;
        this.total_results = 0;
    }

    // Methods
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TrendingMovie> getResults() {
        return results;
    }

    public void setResults(List<TrendingMovie> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrendingResponse that = (TrendingResponse) o;
        return page == that.page && total_pages == that.total_pages && total_results == that.total_results && Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, results, total_pages, total_results);
    }

    @Override
    public String toString() {
        return "TrendingResponse{" +
                "page=" + page +
                ", results=" + results +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
