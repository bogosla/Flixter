package com.example.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    private final String title;
    private final String overview;
    private final String poster_path;
    private final String backdrop_path;
    private final float vote_average;
    private final static String BASE_URL = "https://image.tmdb.org/t/p/w500";


    public Movie(String title, String overview, String poster_path, String backdrop_path, float vote_average) {
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;
    }

    public boolean has_5_star() {
        return ((int) this.vote_average) > 5;
    }

    public static Movie fromJson(JSONObject json) throws JSONException {
        return new Movie(json.getString("title"), json.getString("overview"), json.getString("poster_path"), json.getString("backdrop_path"), (float)(json.getDouble("vote_average")));
}

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return BASE_URL + poster_path;
    }

    public String getBackdrop_path() {
        return BASE_URL + backdrop_path;
    }
    public float getVote_average() {
        return vote_average;
    }
}
