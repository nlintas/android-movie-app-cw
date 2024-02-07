package york.mobiledev.nikoslintas.movieappcw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import york.mobiledev.nikoslintas.movieappcw.models.SearchResponse;
import york.mobiledev.nikoslintas.movieappcw.models.SpecificMovie;
import york.mobiledev.nikoslintas.movieappcw.models.TrendingMovie;
import york.mobiledev.nikoslintas.movieappcw.models.TrendingResponse;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    // Vars
    private RequestQueue requestQueue;
    private Gson gson;
    private static int page = 1;
    private static int totalPages = 0;
    RecyclerViewAdapter recyclerViewAdapter;
    List<TrendingMovie> movies = new ArrayList<>();
    // UI Components
    Button prev_btn;
    Button next_btn;
    SearchView searchbox;
    RecyclerView list;
    TextView pageNum;
    ProgressBar progressBar;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // Standard Stuff
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Vars
        prev_btn = findViewById(R.id.prev_btn);
        next_btn = findViewById(R.id.next_btn);
        searchbox = findViewById(R.id.search);
        list = findViewById(R.id.recyclerview);
        pageNum = findViewById(R.id.page_num);
        progressBar = findViewById(R.id.loading_movie_list);

        // Initialise Volley Queue
        requestQueue = Volley.newRequestQueue(this);
        // Initialize GSON
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        // Button for Next Page Trending Movies
        prev_btn.setOnClickListener(v -> {
            fetchTrendingMovies(-1);
        });

        // Button for Previous Page Trending Movies
        next_btn.setOnClickListener(v -> {
            fetchTrendingMovies(1);
        });
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
                if (movies.isEmpty()) {
                    // Hide View Items
                    hideUI();
                    // Run the fetch trending method when the app opens
                    fetchTrendingMovies(0);
                }
                networkResponse.setText("Wifi or mobile data connection available!");
//                Uncomment to test network
//                networkResponse.show();
            }
            // No connectivity.
        } else {
            Intent intent = new Intent(this, LostConnectionActivity.class);
            startActivity(intent);
        }

        // Listener for searchbox to control the underlying progress bar.
        searchbox.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideProgressBar();
                } else {
                    revealProgressBar();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Re-create (empty) the list once the app goes to the background
        movies = new ArrayList<>();
    }

    // Internal Methods
    // *\/* *\/* *\/* Trending Movies *\/* *\/* *\/*
    private void fetchTrendingMovies(int action) {
        // Re-create (empty) the list once the page changes
        movies = new ArrayList<>();
        if (action == 1 && page <= totalPages) {
            page++;
        } else if (action == -1 && page > 1) {
            page--;
        }
        pageNum.setText(String.valueOf(page));
        String endpoint = "https://api.themoviedb.org/3/trending/movie/day?api_key=60feb8e6285a5489b5004719a740386c&media_type=movie&time_window=day&page=" + page;
        // Create Request
        StringRequest request = new StringRequest(Request.Method.GET, endpoint, onTrendingMoviesLoaded, onTrendingMoviesError);
        // Add Request
        requestQueue.add(request);
    }

    private final Response.Listener<String> onTrendingMoviesLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // Convert JSON to POJO
            TrendingResponse trendingResponse = gson.fromJson(response, TrendingResponse.class);
            totalPages = trendingResponse.getTotal_pages();
            // Print all Trending Movies
            for (TrendingMovie item : trendingResponse.results) {
                item.transformSelfHyperlinks();
                //Log.i("Trending Movie ", String.valueOf(item));
                movies.add(item);
            }
            recyclerAdapterFill();
            // Print Trending Response
            Log.i("Trending Response", trendingResponse.toString());
            // Print Sum of All Trending Movies Received
            Log.i("Trending Movies", String.valueOf(trendingResponse.results.size()));
            // Unhide Items
            revealUI();
        }
    };
    private final Response.ErrorListener onTrendingMoviesError = error -> Log.e("Trending Response Error", error.toString());

    private void recyclerAdapterFill() {
        recyclerViewAdapter = new RecyclerViewAdapter(this, movies);
        list.setAdapter(recyclerViewAdapter);
        searchbox.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        hideUI();
        searchForMovie(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {
            revealPagingUI();
            fetchTrendingMovies(0);
        } else {
            recyclerViewAdapter.filter(newText);
        }
        return true;
    }

    // *\/* *\/* *\/* Search for Movies *\/* *\/* *\/*
    private void searchForMovie(String query) {
        // Re-create (empty) the list once the page changes
        movies = new ArrayList<>();
        String endpoint = "https://api.themoviedb.org/3/search/movie?api_key=60feb8e6285a5489b5004719a740386c&language=en-US&query=" + query + "&page=1&include_adult=true";
        // Create Request
        StringRequest request = new StringRequest(Request.Method.GET, endpoint, onMovieSearchLoaded, onMovieSearchError);
        // Add Request
        requestQueue.add(request);
    }

    private final Response.Listener<String> onMovieSearchLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // Convert JSON to POJO
            SearchResponse searchResponse = gson.fromJson(response, SearchResponse.class);
            // Print all Trending Movies
            for (TrendingMovie item : searchResponse.results) {
                item.transformSelfHyperlinks();
                Log.i("Searched for Movie ", String.valueOf(item));
                movies.add(item);
            }
            recyclerAdapterFill();
            // Print Trending Response
            Log.i("Search Response", searchResponse.toString());
            // Print Sum of All Trending Movies Received
            Log.i("Search For Movies", String.valueOf(searchResponse.results.size()));
            // Unhide Items
            revealUI();
            hidePagingUI();
        }
    };
    private final Response.ErrorListener onMovieSearchError = error -> Log.e("Movie Search Error", error.toString());

    private void hideUI() {
        prev_btn.setVisibility(View.GONE);
        next_btn.setVisibility(View.GONE);
        searchbox.setVisibility(View.GONE);
        list.setVisibility(View.GONE);
        pageNum.setVisibility(View.GONE);
    }

    private void revealUI() {
        prev_btn.setVisibility(View.VISIBLE);
        next_btn.setVisibility(View.VISIBLE);
        searchbox.setVisibility(View.VISIBLE);
        list.setVisibility(View.VISIBLE);
        pageNum.setVisibility(View.VISIBLE);
    }

    private void hidePagingUI() {
        prev_btn.setVisibility(View.GONE);
        next_btn.setVisibility(View.GONE);
        pageNum.setVisibility(View.GONE);
    }

    private void revealPagingUI() {
        prev_btn.setVisibility(View.VISIBLE);
        next_btn.setVisibility(View.VISIBLE);
        pageNum.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


    private void revealProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

}

