package york.mobiledev.nikoslintas.movieappcw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import york.mobiledev.nikoslintas.movieappcw.models.TrendingMovie;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    // Declare Variables
    private Context context;
    private List<TrendingMovie> movieList;
    private ArrayList<TrendingMovie> arraylist;

    public RecyclerViewAdapter(Context context, List<TrendingMovie> movieList) {
        this.context = context;
        this.movieList = movieList;
        arraylist = new ArrayList<>();
        arraylist.addAll(movieList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView movie_title;
        private ImageView movie_image;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            movie_title = view.findViewById(R.id.movie_title);
            movie_image = view.findViewById(R.id.movie_image);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_view_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.movie_title.setText(movieList.get(position).getTitle());
        ImageView imageView = viewHolder.movie_image;

        if(movieList.get(position).getPoster().contains("null"))
        {
            imageView.setImageResource(R.drawable.no_image_available);
        }
        else {
            Glide.with(viewHolder.itemView.getContext())
                    .load(movieList.get(position).getPoster())
                    .into(imageView);
        }

        viewHolder.movie_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = viewHolder.getAdapterPosition();
                openMovieUI(movieList.get(currentPosition).getId());
            }
        });

        viewHolder.movie_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = viewHolder.getAdapterPosition();
                openMovieUI(movieList.get(currentPosition).getId());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movieList.size();
    }

    // Filter Search
    public void filter(String charText) {
        // Gets the query text
        charText = charText.toLowerCase(Locale.getDefault());
        // Empties the local list that will be used as a result.
        movieList.clear();
        // Checks if the search query is not empty.
        if (charText.length() == 0) {
            // Refills the list with all the movies.
            movieList.addAll(arraylist);
        } else {
            // For each movie in the list with all the movies
            for (TrendingMovie movie : arraylist) {
                // if the lowercase title contains the query text
                if (movie.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    // Add it to the result list.
                    movieList.add(movie);
                }
            }
        }
        // Notify the adapter that content changed.
        notifyDataSetChanged();
    }

    // Go into image
    public void openMovieUI(int id){
        Intent intent = new Intent(context, SpecificMovieActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("trendingMovieID", String.valueOf(id));
        context.startActivity(intent);
    }

}

