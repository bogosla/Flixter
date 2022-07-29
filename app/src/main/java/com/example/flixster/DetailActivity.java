package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.text.MessageFormat;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {
    private YouTubePlayerFragment youtubeFragment;
    public static final String TAG = "DetailActivity";
    private static final String API_KEY_YOUTUBE = "AIzaSyDoRaxkpZfogzp7OELF--yqC8EaoQQV5TI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        // Fragment Youtube
        youtubeFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeFragment);

        TextView title = findViewById(R.id.tvDetailTitle);
        TextView popularity = findViewById(R.id.tvDetailPopularity);
        TextView overview = findViewById(R.id.tvDetailOverview);
        RatingBar rating = findViewById(R.id.rbDetailRating);
        // Populate
        title.setText(movie.getTitle());
        popularity.setText(MessageFormat.format("Popularity : {0}", movie.getPopularity()));
        overview.setText(movie.getOverview());
        rating.setRating(movie.getVote_average());

        // Making request to get the KEY
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", MainActivity.APIKEY);

        client.get(movie.getMovie_path(), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    String key = results.getJSONObject(0).getString("key");
                    Log.d(TAG, key.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "Request Failed");
            }
        });
    }

    // Initialize Youtube
    private void initializeYoutube(String api_key, String key) {
        youtubeFragment.initialize(api_key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "Player OK");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "Player Failed");
            }
        });
    }
}