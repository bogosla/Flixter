package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import com.example.flixster.models.Movie;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rcMovies = (RecyclerView) findViewById(R.id.rcMovies);
        ArrayList<Movie> movies = this.getMovies();
        Log.d(TAG, Integer.toString(movies.size()));

        MovieAdapter adapter = new MovieAdapter(movies, getApplicationContext());
        rcMovies.setAdapter(adapter);
        rcMovies.setHasFixedSize(true);
        rcMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    private ArrayList<Movie> getMovies() {
        ArrayList<Movie> _movies = new ArrayList<>();
        _movies.add(new Movie("Hello CodePath", "Android course university", "http", "http", 5.7f));
        _movies.add(new Movie("Bootcamp CodePath", "Android course university", "http", "http", 4.7f));
        _movies.add(new Movie("Marvels, Doctor Me", "I like this", "http", "http", 7.0f));
        return _movies;
    }
}