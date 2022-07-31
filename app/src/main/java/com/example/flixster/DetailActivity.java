package com.example.flixster;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityDetailBinding;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.text.MessageFormat;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {
    public static final String TAG = "DetailActivity";
    private ActivityDetailBinding binding;
    private static final String API_KEY_YOUTUBE = "AIzaSyDoRaxkpZfogzp7OELF--yqC8EaoQQV5TI";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        binding.tvTitle.setText(movie.getTitle());
        binding.tvPopularity.setText(MessageFormat.format("Popularity : {0}", movie.getPopularity()));
        binding.tvReleaseDate.setText(MessageFormat.format("Release date : {0}", movie.getRelease_date()));

        binding.tvOverview.setText(movie.getOverview());

        binding.rbRating.setRating(movie.getVote_average());


        // Making request to get the KEY
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", MainActivity.API_KEY_MOVIE);

        client.get(movie.getMovie_path(), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    String key = results.getJSONObject(0).getString("key");
                    initializeYoutube(key, movie);
                } catch (JSONException e) { e.printStackTrace(); }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "Request Failed");
            }
        });
    }

    // Initialize Youtube
    private void initializeYoutube(String key, Movie movie) {
        binding.youtubePlayer.initialize(API_KEY_YOUTUBE, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if(movie.has_5_star())
                    youTubePlayer.loadVideo(key);
                else
                    youTubePlayer.cueVideo(key);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e(TAG, "Player Failed");
            }
        });
    }
}