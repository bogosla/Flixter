package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String API_KEY_MOVIE = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String ENDPOINT = "https://api.themoviedb.org/3/movie/now_playing";
    private MovieAdapter adapter;
    private final List<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        RecyclerView rcMovies = binding.rcMovies;

        fetchMovies();
        adapter = new MovieAdapter(movies, this);

        adapter.setClickListener((view, position, movie) -> {
            // start Detail activity
            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            i.putExtra("movie", Parcels.wrap(movie));

            // Pairs for transitions
            // Pair<View, String> imageTransition = Pair.create(view.findViewById(R.id.itemImg), "image");
//            View title = view.findViewById(R.id.itemTitle);
//            if (title != null) {
                // Pair<View, String> titleTransition = Pair.create(title, "title");
                // Pair<View, String> overviewTransition = Pair.create(view.findViewById(R.id.itemOverview), "overview");
//            }
            // ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, transiton , "title");
            // ActivityCompat.titlestartActivity(MainActivity.this, i, options.toBundle());
             startActivity(i);
        });

        rcMovies.setAdapter(adapter);
        rcMovies.setHasFixedSize(true);
        rcMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", API_KEY_MOVIE);

        client.get(ENDPOINT, params, new JsonHttpResponseHandler() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                if (statusCode == 200)
                    try {
                        JSONArray results = json.jsonObject.getJSONArray("results");
                        for (int i=0; i < results.length(); i++ ) {
                            movies.add(Movie.fromJson(results.getJSONObject(i)));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) { Log.e(TAG, e.toString()); }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "request failed");
            }
        });
    }
}