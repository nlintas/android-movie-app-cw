package york.mobiledev.nikoslintas.movieappcw;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView movieTitle;
    ImageView imageView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        movieTitle = itemView.findViewById(R.id.movie_title);
        imageView = itemView.findViewById(R.id.movie_image);
    }
}
