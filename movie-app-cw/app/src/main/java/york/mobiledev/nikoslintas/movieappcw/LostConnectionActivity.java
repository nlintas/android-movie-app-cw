package york.mobiledev.nikoslintas.movieappcw;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LostConnectionActivity extends AppCompatActivity {
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.lost_connection_activity);
    }

    @Override
    protected void onPostResume() {
        // Super
        super.onPostResume();

        // Set No Network Image
        ImageView image = (ImageView) findViewById(R.id.no_con_image);
        image.setImageResource(R.drawable.no_signal);

        // Detect Network Availability
        ConnectivityManager manager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (null != activeNetwork) {
            // Connection available.
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
