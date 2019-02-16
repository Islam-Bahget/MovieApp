package com.example.islam.movies.search_mvp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.bumptech.glide.Glide;
import com.example.islam.movies.Const;
import com.example.islam.movies.R;
import com.example.islam.movies.model.Movie;
import com.example.islam.movies.model.TV;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> implements Filterable {

    private List<Movie> movieList;
    private List<TV> tvList;
    private Context context;

    private int type;
    private OnSearchMovieClickListener listener;

    public SearchAdapter(Context context, int type) {

        this.context = context;
        this.type = type;
    }

    public interface OnSearchMovieClickListener {
        void onSearchMovieClicked(int position);
    }

    public void setOnSearchMovieClickListener(OnSearchMovieClickListener listener) {

        this.listener = listener;
    }

    public void setTvList(List<TV> tvList) {
        this.tvList = tvList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchHolder(LayoutInflater.from(context).inflate(R.layout.search_list_item, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {

        switch (type) {
            case Const.MOVIES:
                Movie movie = movieList.get(position);

                assert movie != null;
                String year = movie.getReleaseDate();

                Glide.with(context).load(Const.IMAGE_URL + movie.getPosterPath()).into(holder.movieImage);
                holder.movieTitle.setText(movie.getTitle());
                if (!year.isEmpty()) {
                    holder.movieYear.setText(year.subSequence(0, 4));

                } else {
                    holder.movieYear.setText("");

                }
                break;
            case Const.TV:
                TV tv = tvList.get(position);

                assert tv != null;
                String date = tv.getFirstAirDate();

                Glide.with(context).load(Const.IMAGE_URL + tv.getPosterPath()).into(holder.movieImage);
                holder.movieTitle.setText(tv.getName());

                if (!date.isEmpty()) {
                    holder.movieYear.setText(date.subSequence(0, 4));

                } else {
                    holder.movieYear.setText("");

                }
        }

    }

    @Override
    public int getItemCount() {
        switch (type) {
            case Const.MOVIES:
                return movieList.size();
            case Const.TV:
                return tvList.size();
            default:
                return 0;
        }

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnSearchMovieClickListener listener;
        @BindView(R.id.search_movie_poster)
        AppCompatImageView movieImage;
        @BindView(R.id.search_movie_title)
        AppCompatTextView movieTitle;
        @BindView(R.id.search_movie_year)
        AppCompatTextView movieYear;

        public SearchHolder(View itemView, OnSearchMovieClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            listener.onSearchMovieClicked(getAdapterPosition());
        }
    }
}
