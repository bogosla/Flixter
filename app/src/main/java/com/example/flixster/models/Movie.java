package com.example.flixster.models;

public class Movie {
    private String title;
    private  String overview;
    private String poster_path;
    private  String backdrop_path;
    private float vote_average;


    public Movie(String title, String overview, String poster_path, String backdrop_path, float vote_average) {
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;
    }

    public boolean has_5_star() {
        if (((int)this.vote_average) >= 5) {
            return true;
        }
        return false;
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
