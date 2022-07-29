package com.example.flixster;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.databinding.MovieItemBinding;
import com.example.flixster.databinding.MovieItemStarBinding;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HAS_5_STAR = 1;
    private static final int HAS_NOT_STAR = 0;

    private final List<Movie> mMovies;
    private final Context mContext;

    private OnClickListener mClickListener;

    public void setClickListener(OnClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    interface OnClickListener {
        void onItemClicked(View view, int position, Movie movie);
    }

    public MovieAdapter(List<Movie> movies, Context ctx) {
        this.mMovies = movies;
        this.mContext = ctx;
    }
    
    @Override
    public int getItemViewType(int position) {
        return (mMovies.get(position)).has_5_star() ? HAS_5_STAR : HAS_NOT_STAR;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == HAS_5_STAR && getOrientation() == Configuration.ORIENTATION_PORTRAIT) {
            MovieItemStarBinding bindingStar = DataBindingUtil.inflate(inflater, R.layout.movie_item_star, parent, false);
            return new ViewHolderStar(bindingStar);
        }
        MovieItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.movie_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        int radius = 12;

        RequestOptions options = new RequestOptions()
                .transform(new RoundedCorners(radius))
                .centerCrop()
                .placeholder(R.drawable.movie)
                .error(R.mipmap.ic_launcher_round);

        // if orientation in portrait and movie got 5 star, display backdrop_image
        if (holder instanceof ViewHolderStar)
            ((ViewHolderStar)holder).onBind(movie, options);
        else
            ((ViewHolder)holder).onBind(movie, options);
    }


    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    private int getOrientation() {
        return mContext.getResources().getConfiguration().orientation;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public MovieItemBinding itemBinding;

        public ViewHolder(@NonNull MovieItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;

            itemBinding.getRoot().setOnClickListener(view -> mClickListener.onItemClicked(view, getAdapterPosition(), mMovies.get(getAdapterPosition())));
        }

        public void onBind(Movie m, RequestOptions options) {
            if (getOrientation() == Configuration.ORIENTATION_LANDSCAPE)
                Glide.with(mContext).load(m.getBackdrop_path()).apply(options).into(itemBinding.itemPoster);
            else
                Glide.with(mContext).load(m.getPoster_path()).apply(options).into(itemBinding.itemPoster);
            itemBinding.itemTitle.setText(m.getTitle());
            itemBinding.itemOverview.setText(m.getOverview());
            itemBinding.executePendingBindings();
        }
    }

    // Holder for item with more 5 star
    public class ViewHolderStar extends RecyclerView.ViewHolder {
        public MovieItemStarBinding itemBinding;

        public ViewHolderStar(@NonNull MovieItemStarBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;

            itemBinding.itemPoster2.setOnClickListener(view -> mClickListener.onItemClicked(view, getAdapterPosition(), mMovies.get(getAdapterPosition())));
        }
        public void onBind(Movie m, RequestOptions options) {
            Glide.with(mContext).load(m.getBackdrop_path()).apply(options).into(itemBinding.itemPoster2);
            itemBinding.executePendingBindings();
        }
    }
}


