package com.amyhuyen.flixster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amyhuyen.flixster.models.GlideApp;
import com.amyhuyen.flixster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.amyhuyen.flixster.models.MovieAdapter.backdropUrl;
import static com.amyhuyen.flixster.models.MovieAdapter.imageUrl;

public class MovieDetailsActivity extends AppCompatActivity {

    // constants
    // the base URL for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    public final static String API_KEY_PARAM = "api_key";
    // tag for logging from this activity
    public final static String TAG = "MovieListActivity";

    // the movie to display
    Movie movie;
    // instance fields
    AsyncHttpClient client;
    // movie key
    String key;

    // the view objects
    @BindView (R.id.tvTitle) TextView tvTitle;
    @BindView (R.id.tvOverview) TextView tvOverview;
    @BindView (R.id.rbVoteAverage) RatingBar rbVoteAverage;
    @BindView (R.id. tvReleaseDate) TextView tvReleaseDate;
    @Nullable @BindView (R.id.ivBackdropImage) ImageView ivBackdropImage;
    @Nullable @BindView (R.id.ivPosterImage) ImageView ivPosterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // resolve the view objects
        ButterKnife.bind(this);

        client = new AsyncHttpClient();

        // scrolling overview
        tvOverview.setMovementMethod(new ScrollingMovementMethod());

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        // Log debug
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

       // set the title, overview, and release date
       tvTitle.setText(movie.getTitle());
       tvOverview.setText(movie.getOverview());
       tvReleaseDate.setText("Release Date: " + movie.getReleaseDate());

//        getVideo();

       // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage > 0? voteAverage/2.0f: voteAverage);

        GlideApp.with(this)
                .load(getIntent().getStringExtra(imageUrl))
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .into(ivPosterImage);

        GlideApp.with(this)
                .load(getIntent().getStringExtra(backdropUrl))
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .error(R.drawable.flicks_backdrop_placeholder)
                .into(ivBackdropImage);
    }

//    @OnClick(R.id.ivBackdropImage)
//    public void getVideo(){
//        // create the url
//        String url = API_BASE_URL + "/movie/"+ movie.getId() +"/videos";
//        // set the request parameters
//        RequestParams params = new RequestParams();
//        params.put(API_KEY_PARAM, getString(R.string.api_key)); // API key, always required
//        // execute a GET request expecting a JSON object response
//        client.get(url, params, new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // load the results into movie list
//                try {
//                    JSONArray result = response.getJSONArray("results");
//                    JSONObject vid1 = result.getJSONObject(0);
//                    key = (String) vid1.get("key");
//                    Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
//                    intent.putExtra("key", key);
//                    startActivity(intent);
//                } catch (JSONException e) {
//                    logError("Failed to get video", e, true);
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                logError("Failed to get data from now playing endpoint", throwable, true);
//            }
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object){
//                logError("Failed to get data from now playing endpoint", throwable, true);
//            }
//        });
//    }
//
//    // handle error, log and alert user
//    private void logError(String message, Throwable error, boolean alertUser){
//        // always log the error
//        Log.e(TAG, message, error);
//        // alert the user to avoid silent errors
//        if (alertUser){
//            // show a long toast with the error message
//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//        }
//    }
}
