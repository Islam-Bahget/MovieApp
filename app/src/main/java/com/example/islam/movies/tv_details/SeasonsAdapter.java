package com.example.islam.movies.tv_details;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.islam.movies.Const;
import com.example.islam.movies.R;
import com.example.islam.movies.model.TvSeason;
import com.silencedut.expandablelayout.ExpandableLayout;

import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.SeasonHolder> {

    private List<TvSeason> seasonList;
    private Context context;
    private HashSet<Integer> hashSet;

    public SeasonsAdapter(Context context, List<TvSeason> seasonList) {
        this.context = context;
        this.seasonList = seasonList;
        hashSet = new HashSet<>();
    }

    @NonNull
    @Override
    public SeasonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            return new SeasonHolder(LayoutInflater.from(context).
                    inflate(R.layout.season_item, parent, false));
        } else {
            return new SeasonHolder(LayoutInflater.from(context).
                    inflate(R.layout.season_item_pre, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull SeasonHolder holder, int position) {

        TvSeason season = seasonList.get(position);
        assert season != null;
        holder.seasonName.setText(season.getName());
        String year = season.getAirDate();
        if (year != null) {
            holder.seasonYear.setText(year.subSequence(0, 4));
        } else {
            holder.seasonYear.setText("");
        }

        holder.seasonEpisodes.setText(String.valueOf(season.getEpisodesCount()) + " Episodes");
        holder.seasonOverView.setText(season.getOverView());
        Glide.with(context).load(Const.IMAGE_URL + season.getPosterPath())
                .into(holder.seasonImage);

        holder.updateItem(position);
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    class SeasonHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.expand_layout)
        ExpandableLayout layout;
        @BindView(R.id.season_name)
        AppCompatTextView seasonName;
        @BindView(R.id.season_image)
        AppCompatImageView seasonImage;
        @BindView(R.id.season_overview)
        AppCompatTextView seasonOverView;
        @BindView(R.id.season_episodes)
        AppCompatTextView seasonEpisodes;
        @BindView(R.id.season_year)
        AppCompatTextView seasonYear;
        @BindView(R.id.show_desc)
        AppCompatTextView show;


        SeasonHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void updateItem(final int position) {
            layout.setOnExpandListener(b -> registerExpand(position, show));
        }

        private void registerExpand(int position, TextView textView) {
            if (hashSet.contains(position)) {
                removeExpand(position);
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                textView.setText(R.string.show_overview);
            } else {
                addExpand(position);
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                textView.setText(R.string.hide_overview);
            }
        }

        private void removeExpand(int position) {
            hashSet.remove(position);
        }

        private void addExpand(int position) {
            hashSet.add(position);
        }
    }
}
