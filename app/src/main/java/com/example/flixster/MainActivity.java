package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcException;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    List<Movie> movies;
    private static final String APIKEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static String ENDPOINT = "https://api.themoviedb.org/3/movie/now_playing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rcMovies = (RecyclerView) findViewById(R.id.rcMovies);
        movies = new ArrayList<>();
        movies = this.getMovies();
        try {
            fetchMovies();
        } catch ( Exception e) {
            Log.d(TAG, "ERROR Request");
        }

        Log.d(TAG, Integer.toString(movies.size()));

        MovieAdapter adapter = new MovieAdapter(movies, getApplicationContext());
        rcMovies.setAdapter(adapter);
        rcMovies.setHasFixedSize(true);
        rcMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void fetchMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", APIKEY);
        client.get(ENDPOINT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                // todo
                Log.d(TAG, Integer.toString(statusCode));
                if (statusCode == 200)
                    try {
                        JSONArray m = json.jsonObject.getJSONArray("results");
                        for (int i=0; i < m.length(); i++ ) {
                            m[i]
                        }
                    } catch (JSONException e) {
                        //todo
                    }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                // todo
                Log.d(TAG, "request failed");
            }
        });
    }
    private ArrayList<Movie> getMovies() {
        ArrayList<Movie> _movies = new ArrayList<>();
        _movies.add(new Movie("Hello CodePath", "Android course university", "http", "http", 5.7f));
        _movies.add(new Movie("Bootcamp CodePath", "Android course university", "http", "http", 4.7f));
        _movies.add(new Movie("Marvels, Doctor Me", "I like this", "http", "http", 7.0f));
        return _movies;
    }
}