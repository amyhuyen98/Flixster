package com.amyhuyen.flixster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amyhuyen.flixster.models.Movie;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {
    // the movie to display
    Movie movie;

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


       // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage > 0? voteAverage/2.0f: voteAverage);
    }
}
