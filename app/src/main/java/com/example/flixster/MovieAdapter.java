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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Movie> mMovies;
    private static final int HAS5STAR = 1;
    private Context mContext;

    public MovieAdapter(List<Movie> movies, Context ctx) {
        this.mMovies = movies;
        this.mContext = ctx;
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
        //
        if (viewType == HAS5STAR && getOrientation() == 1)
            return new ViewHolderStar(inflater.inflate(R.layout.movie_item_star, parent, false));
        return new ViewHolder(inflater.inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round);
        // if orientation in portrait and movie got 5 star, display backdrop_image
        if (movie.has_5_star() && getOrientation() == 1) {
             Glide.with(mContext).load(movie.getBackdrop_path()).apply(options).into(((ViewHolderStar)holder).imageview2);
        } else {
            // else no 5 star, display title, overview and poster_image (still in portrait)
            // else, display backdrop_image in landscape
            ((ViewHolder)holder).titleTextview.setText(movie.getTitle());
            ((ViewHolder)holder).overviewTextview.setText(movie.getOverview());
            if (getOrientation() == 1)
                 Glide.with(mContext).load(movie.getPoster_path()).apply(options).into(((ViewHolder)holder).imageview);
            else
                Glide.with(mContext).load(movie.getBackdrop_path()).apply(options).into(((ViewHolder)holder).imageview);
        }
    }


    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    private int getOrientation() {
        // return 1 if portrait else 0 for landscape
        int orientation = mContext.getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 0;
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


