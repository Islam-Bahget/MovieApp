package com.example.islam.movies.fragments;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.example.islam.movies.Const;
import com.example.islam.movies.R;
import com.example.islam.movies.model.Movie;
import com.example.islam.movies.model.TV;

import java.util.List;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.MovieViewHolder> {
    Context context;
    List<Movie> movieList;
    List<TV> tvList;

    private OnLongItemListene listene;
    private OnMovieClickListener listener;
    private int type;

    public ReAdapter(Context context, int type) {
        this.context = context;

        this.type = type;
    }


    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }


    public void setTvList(List<TV> tvList) {
        this.tvList = tvList;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

            Log.d("ver", "Loli");
            return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false), listener, listene);

        } else {
            Log.d("ver", "pre Loli");
            return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_item_pre, parent, false), listener, listene);

        }
    }

    interface OnLongItemListene {
        void onItem(int position);
    }

    void setOnLong(OnLongItemListene listene) {
        this.listene = listene;
    }

    public interface OnMovieClickListener {
        void onMovieClicked(int position);
    }

    public void setOnMovieClicked(OnMovieClickListener listener) {

        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        int lastPosition = -1;
        switch (type) {
            case Const.MOVIES:
                Movie movie = movieList.get(position);
//        holder.movieTitle.setText(movie.getTitle());
                //  holder.rating.setText(movie.getVoteAverage().toString());
                Glide.with(context).load(Const.IMAGE_URL + movie.getPosterPath())
                        .into(holder.imageView);

                break;
            case Const.TV:
                TV tv = tvList.get(position);
                Glide.with(context).load(Const.IMAGE_URL + tv.getPosterPath())
                        .into(holder.imageView);
                break;

        }

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.tr
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);

        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        switch (type) {
            case Const.MOVIES:
                return movieList.size();
            case Const.TV:
                return tvList.size();

        }
        return 0;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        AppCompatTextView movieTitle;

        AppCompatTextView rating;
        AppCompatImageView imageView;

        View view;


        OnMovieClickListener listener;

        OnLongItemListene listene;

        MovieViewHolder(View v, OnMovieClickListener listener, OnLongItemListene listene) {
            super(v);
            this.listener = listener;

            this.listene = listene;
            v.setOnClickListener(this);
//            movieTitle = v.findViewById(R.id.movie_title);
            //   rating = v.findViewById(R.id.movie_rate);
            imageView = itemView.findViewById(R.id.movie_poster);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onMovieClicked(getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View view) {
            listene.onItem(getAdapterPosition());
            view.setAlpha(0.6F);
            return true;
        }
    }

    public void addAll(List<Movie> movieList) {

        for (Movie movie : movieList) {
            addMovie(movie);
        }
    }

    public void clear() {
        movieList.clear();
    }

    private void addMovie(Movie movie) {
        movieList.add(movie);
        notifyItemInserted(movieList.size() - 1);
    }

}
