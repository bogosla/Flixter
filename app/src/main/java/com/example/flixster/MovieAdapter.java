package com.example.flixster;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Movie> mMovies;
    private static final int HAS5STAR = 1;
    private Context mCtx;

    public MovieAdapter(List<Movie> movies, Context ctx) {
        this.mMovies = movies;
        this.mCtx = ctx;
    }
    
    @Override
    public int getItemViewType(int position) {
        Movie current = mMovies.get(position);
        if (current.has_5_star()) {
            return HAS5STAR;
        }
        return 0;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Log.d("ADAPTER", Integer.toString(viewType));
        if (viewType == HAS5STAR && getOrientation() == 1)
            return new ViewHolderStar(inflater.inflate(R.layout.movie_item_star, parent, false));
        return new ViewHolder(inflater.inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        if (movie.has_5_star() && getOrientation() == 1) {
            ((ViewHolderStar)holder).imageview2.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            ((ViewHolder)holder).titleTextview.setText(movie.getTitle());

            ((ViewHolder)holder).overviewTextview.setText(movie.getOverview());
            if (getOrientation() == 1)
                ((ViewHolder)holder).imageview.setImageResource(R.mipmap.ic_launcher);
            else
                ((ViewHolder)holder).imageview.setImageResource(R.mipmap.ic_launcher_round);


        }
    }


    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    private int getOrientation() {
        // return 1 if portrait else 0 for landscape
        int orientation = this.mCtx.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            return 1;
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextview;
        public TextView overviewTextview;
        public ImageView imageview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTextview = itemView.findViewById(R.id.itemTitle);
            this.imageview = itemView.findViewById(R.id.itemImg);
            this.overviewTextview = itemView.findViewById(R.id.itemOverview);
        }
    }

    public static class ViewHolderStar extends RecyclerView.ViewHolder {
        public ImageView imageview2;

        public ViewHolderStar(@NonNull View itemView) {
            super(itemView);
            this.imageview2 = itemView.findViewById(R.id.itemImg2);
        }
    }
}


