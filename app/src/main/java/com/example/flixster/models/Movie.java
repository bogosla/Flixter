package com.example.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Movie {
    private int movie_id;
    private String title;
    private String overview;
    private String poster_path;
    private String backdrop_path;
    private float vote_average;
    private String release_date;
    private double popularity;
    private Boolean adult;
    private final static String BASE_URL = "https://image.tmdb.org/t/p/w500";
    private final String BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie/%d/videos";

    // Needed by Parceler library
    public Movie() {}

    public Movie(String title, String overview, String poster_path, String backdrop_path, float vote_average, int id, String release_date, double popularity, Boolean adult) {
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;
        this.movie_id = id;
        this.release_date = release_date;
        this.popularity = popularity;
        this.adult = adult;
    }


    public static Movie fromJson(JSONObject json) throws JSONException {
        return new Movie(
                json.getString("title"),
                json.getString("overview"),
                json.getString("poster_path"),
                json.getString("backdrop_path"),
                (float)(json.getDouble("vote_average")),
                json.getInt("id"),
                json.getString("release_date"),
                json.getDouble("popularity"),
                json.getBoolean("adult"));
    }

    public boolean has_5_star() {
        return ((int) this.vote_average) > 5;
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

    public String getBackdrop_path() { return BASE_URL + backdrop_path; }

    public String getMovie_path() { return String.format(BASE_URL_MOVIE, movie_id); }

    public float getVote_average() { return vote_average; }

    public double getPopularity() { return popularity; }

    public String getRelease_date() { return release_date; }

    public Boolean getAdult() { return adult; }

    @Override
    public String toString() { return String.format("Movie{%s}", title); }
}
