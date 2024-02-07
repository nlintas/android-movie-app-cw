package york.mobiledev.nikoslintas.movieappcw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import york.mobiledev.nikoslintas.movieappcw.models.SpecificMovie;

public class SpecificMovieActivity extends AppCompatActivity {
    private static int specificMovieID = 0;
    private RequestQueue requestQueue;
    private Gson gson;
    SpecificMovie movie;
    ProgressBar progressBar;
    ImageView movie_cover;
    ImageView movie_poster;
    TextView movie_title;
    TextView movie_overview;
    TextView movie_genres;
    TextView movie_imdb_link;
    TextView movie_homepage_link;
    TextView movie_language;
    TextView movie_production_companies;
    TextView movie_release_date;
    TextView movie_budget;
    TextView movie_revenue;
    TextView movie_runtime;
    TextView movie_tagline;
    TextView movie_status;
    TextView movie_vote_average;
    TextView movie_vote_count;
    ImageView adults_only_image;
    LinearLayout adults_only_section;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // Super
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);
        LinearLayout currentLayout =
                findViewById(R.id.specific_movie_id);
        currentLayout.setBackgroundColor(Color.BLACK);

        // Initialise Volley Queue
        requestQueue = Volley.newRequestQueue(this);
        // Initialize GSON
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        specificMovieID = Integer.parseInt(getIntent().getStringExtra("trendingMovieID"));
        // Get the views
        movie_cover = findViewById(R.id.smovie_cover_image);
        movie_poster = findViewById(R.id.smovie_poster_image);
        movie_title = findViewById(R.id.smovie_title);
        movie_overview = findViewById(R.id.smovie_overview);
        movie_genres = findViewById(R.id.smovie_genres);
        movie_homepage_link = findViewById(R.id.smovie_homepage);
        movie_imdb_link = findViewById(R.id.smovie_imdb);
        movie_language = findViewById(R.id.smovie_language);
        movie_production_companies = findViewById(R.id.smovie_production_companies);
        movie_release_date = findViewById(R.id.smovie_release_date);
        movie_budget = findViewById(R.id.smovie_budget);
        movie_revenue = findViewById(R.id.smovie_revenue);
        movie_runtime = findViewById(R.id.smovie_runtime);
        movie_tagline = findViewById(R.id.smovie_tagline);
        movie_status = findViewById(R.id.smovie_status);
        movie_vote_average = findViewById(R.id.smovie_vote_average);
        movie_vote_count = findViewById(R.id.smovie_vote_count);
        adults_only_image = findViewById(R.id.adult_only_image);
        progressBar = findViewById(R.id.loading_specific_movie);
        adults_only_section = findViewById(R.id.adult_section);
    }

    @Override
    protected void onPostResume() {
        // Super call
        super.onPostResume();

        // Checks for network availability
        Toast networkResponse = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        ConnectivityManager manager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (null != activeNetwork) {
            // Wifi or Data is being used.
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // Save bandwidth and processing.
                if (movie == null) {
                    // Hide UI components
                    hideUI();
                    // Run the fetch trending method when the app opens
                    fetchMovie();
                }
                networkResponse.setText("Wifi or mobile data connection available!");
//                networkResponse.show();
            }
            // No connectivity.
        } else {
            Intent intent = new Intent(this, LostConnectionActivity.class);
            startActivity(intent);
        }
    }

    // *\/* *\/* *\/* Specific Movie *\/* *\/* *\/*
    private void fetchMovie() {
        String FETCH_MOVIE_ENDPOINT = "https://api.themoviedb.org/3/movie/" + specificMovieID + "?api_key=60feb8e6285a5489b5004719a740386c&language=en-US";
        // Create Request
        StringRequest request = new StringRequest(Request.Method.GET, FETCH_MOVIE_ENDPOINT, onGetMovieLoaded, onGetMovieError);
        // Add Request
        requestQueue.add(request);
    }

    private final Response.Listener<String> onGetMovieLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // Convert JSON to POJO
            movie = gson.fromJson(response, SpecificMovie.class);
            // Print Movie
            movie.transformSelfHyperlinks();
            Log.i("Specific Movie", movie.toString());
            fillUI();
            // Hide loading screen
            progressBar.setVisibility(View.GONE);
        }
    };
    private final Response.ErrorListener onGetMovieError = error -> Log.e("Specific Movie Error", error.toString());

    private void fillUI() {
        // Set Data
        if(movie.getPoster_path().contains("null"))
        {
            movie_poster.setImageResource(R.drawable.no_image_available);
        }
        else {
            Glide.with(this)
                    .load(movie.getPoster_path())
                    .into(movie_poster);
        }

        if(movie.getBackdrop().contains("null"))
        {
            movie_cover.setImageResource(R.drawable.no_image_available);
        }
        else {
            Glide.with(this)
                    .load(movie.getBackdrop())
                    .into(movie_cover);
        }

        movie_title.setText(movie.getTitle());

        // Check if its adult movie
        if (movie.isAdult()) {
            adults_only_image.setImageResource(R.drawable.adult_only);
            adults_only_section.setVisibility(View.VISIBLE);
        }

        String overviewText = "Overview: " + movie.getOverview();
        movie_overview.setText(overviewText);

        String genresText = movie.getGenres().toString();
        genresText = genresText.replaceAll("\\[", "").replaceAll("\\]", "");
        genresText = "Genres: " + genresText;
        movie_genres.setText(genresText);

        movie_homepage_link.setText(
                Html.fromHtml(
                        "<a href=" + movie.getHomepage() + "> " + movie.getTitle() + " Official Homepage</a> "));
        movie_homepage_link.setMovementMethod(LinkMovementMethod.getInstance());
        movie_homepage_link.setLinkTextColor(Color.BLUE);

        movie_imdb_link.setText(
                Html.fromHtml(
                        "<a href=" + movie.getImdb_link() + "> " + movie.getTitle() + " now on IMDB</a> "));
        movie_imdb_link.setMovementMethod(LinkMovementMethod.getInstance());
        movie_imdb_link.setLinkTextColor(Color.BLUE);

        String language_text = "Language: " + movie.getOriginal_language();
        movie_language.setText(language_text);

        String production_companies_text = movie.getProduction_companies().toString();
        production_companies_text = production_companies_text.replaceAll("\\[", "").replaceAll("\\]", "");
        production_companies_text = "Production Companies: " + production_companies_text;
        movie_production_companies.setText(production_companies_text);

        LocalDate date = LocalDate.parse(movie.getRelease_date());
        String release_date_text = "Release Date: " + date.getDayOfMonth() + " " + date.getMonth() + " " + date.getYear();
        movie_release_date.setText(release_date_text);

        NumberFormat num1 = NumberFormat.getCurrencyInstance(Locale.US);
        String budget_text;
        if (movie.getBudget() != 0)
            budget_text = "Total Budget: " + num1.format(movie.getBudget());
        else
            budget_text = "Budget is not available for this movie!";
        movie_budget.setText(budget_text);

        NumberFormat num2 = NumberFormat.getCurrencyInstance(Locale.US);
        String revenue_text;
        if (movie.getRevenue() != 0)
            revenue_text = "Total Revenue: " + num2.format(movie.getRevenue());
        else
            revenue_text = "Revenue not available for this movie!";
        movie_revenue.setText(revenue_text);

        String runtime_text = "Runtime: " + movie.getRuntime();
        movie_runtime.setText(runtime_text);

        String tagline_text = movie.getTagline();
        movie_tagline.setText(tagline_text);

        String status_text = "Release Status: " + movie.getStatus();
        movie_status.setText(status_text);

        String vote_average_text = "Average User Score: " + movie.getVote_average() + "/10";
        movie_vote_average.setText(vote_average_text);

        String vote_count_text = "Total User Votes: " + movie.getVote_count();
        movie_vote_count.setText(vote_count_text);
        // Show UI components
        revealUI();
    }

    private void revealUI() {
        movie_cover.setVisibility(View.VISIBLE);
        movie_poster.setVisibility(View.VISIBLE);
        movie_title.setVisibility(View.VISIBLE);
        movie_overview.setVisibility(View.VISIBLE);
        movie_genres.setVisibility(View.VISIBLE);
        movie_homepage_link.setVisibility(View.VISIBLE);
        movie_imdb_link.setVisibility(View.VISIBLE);
        movie_language.setVisibility(View.VISIBLE);
        movie_production_companies.setVisibility(View.VISIBLE);
        movie_release_date.setVisibility(View.VISIBLE);
        movie_budget.setVisibility(View.VISIBLE);
        movie_revenue.setVisibility(View.VISIBLE);
        movie_runtime.setVisibility(View.VISIBLE);
        movie_tagline.setVisibility(View.VISIBLE);
        movie_status.setVisibility(View.VISIBLE);
        movie_vote_average.setVisibility(View.VISIBLE);
        movie_vote_count.setVisibility(View.VISIBLE);
        adults_only_image.setVisibility(View.VISIBLE);
    }

    private void hideUI() {
        movie_cover.setVisibility(View.GONE);
        movie_poster.setVisibility(View.GONE);
        movie_title.setVisibility(View.GONE);
        movie_overview.setVisibility(View.GONE);
        movie_genres.setVisibility(View.GONE);
        movie_homepage_link.setVisibility(View.GONE);
        movie_imdb_link.setVisibility(View.GONE);
        movie_language.setVisibility(View.GONE);
        movie_production_companies.setVisibility(View.GONE);
        movie_release_date.setVisibility(View.GONE);
        movie_budget.setVisibility(View.GONE);
        movie_revenue.setVisibility(View.GONE);
        movie_runtime.setVisibility(View.GONE);
        movie_tagline.setVisibility(View.GONE);
        movie_status.setVisibility(View.GONE);
        movie_vote_average.setVisibility(View.GONE);
        movie_vote_count.setVisibility(View.GONE);
        adults_only_image.setVisibility(View.GONE);
        adults_only_section.setVisibility(View.GONE);
    }
}
