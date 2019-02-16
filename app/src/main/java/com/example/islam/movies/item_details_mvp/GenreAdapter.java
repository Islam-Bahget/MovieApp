package com.example.islam.movies.item_details_mvp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islam.movies.R;
import com.example.islam.movies.model.Genre;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreHolder> {

    private Context context;
    private ArrayList<Genre> arrayList;

    public GenreAdapter(Context context, ArrayList<Genre> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public GenreAdapter.GenreHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GenreAdapter.GenreHolder(LayoutInflater.from(context).inflate(R.layout.genere_iem, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.GenreHolder holder, int i) {


        AssetManager assetManager = context.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/dos.ttf");
        holder.genreName.setTypeface(typeface);

        Genre genre = arrayList.get(i);
        holder.genreName.setText(genre.getName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class GenreHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.genre_name)
        AppCompatTextView genreName;

        GenreHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}