package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MovieAdapter adapter;
    private final List<Movie> movies = new ArrayList<>();
    private static final String APIKEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String ENDPOINT = "https://api.themoviedb.org/3/movie/now_playing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rcMovies = findViewById(R.id.rcMovies);
        fetchMovies();
        adapter = new MovieAdapter(movies, this);

        adapter.setClickListener((view, position, movie) -> {
            Log.d(TAG, movie.toString());

            // start Detail activity
            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            i.putExtra("movie", Parcels.wrap(movie));
            startActivity(i);
        });

        rcMovies.setAdapter(adapter);
        rcMovies.setHasFixedSize(true);
        rcMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", APIKEY);

        client.get(ENDPOINT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                if (statusCode == 200)
                    try {
                        JSONArray results = json.jsonObject.getJSONArray("results");
                        for (int i=0; i < results.length(); i++ ) {
                            movies.add(Movie.fromJson(results.getJSONObject(i)));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.e(TAG, e.toString());
                    }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "request failed");
            }
        });
    }
}