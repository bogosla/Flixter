package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        Log.d(TAG, movie.toString());

        TextView title = findViewById(R.id.tvDetailTitle);
        TextView overview = findViewById(R.id.tvDetailOverview);
        RatingBar rating = findViewById(R.id.rbDetailRating);

        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        rating.setRating(movie.getVote_average());


    }
}