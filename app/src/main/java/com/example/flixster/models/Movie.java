package com.example.flixster.models;

import java.util.Map;

public class Movie {
    private final String title;
    private final String overview;
    private final String poster_path;
    private final String backdrop_path;
    private final float vote_average;


    public Movie(String title, String overview, String poster_path, String backdrop_path, float vote_average) {
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;
    }

    public boolean has_5_star() {
        return ((int) this.vote_average) >= 5;
    }

    public Movie fromJson(Map<String, String> json) {

        return new Movie(json.get("title"), json.get("overview"), json.get("poster_path"), json.get("backdrop_path"), Float.parseFloat(json.get("vote_average")));
}

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }
    public float getVote_average() {
        return vote_average;
    }
}
