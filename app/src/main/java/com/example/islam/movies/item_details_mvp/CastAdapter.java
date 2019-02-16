package com.example.islam.movies.item_details_mvp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.islam.movies.R;
import com.example.islam.movies.model.Cast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastHolder> {

    private Context context;
    private ArrayList<Cast> arrayList;

    public CastAdapter(Context context, ArrayList<Cast> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CastHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CastHolder(LayoutInflater.from(context).inflate(R.layout.cast_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastHolder castHolder, int i) {
        String IMAGE_URL = "https://image.tmdb.org/t/p/w500//";

        Cast cast = arrayList.get(i);
        Glide.with(context).load(IMAGE_URL + cast.getProfilePath()).apply(RequestOptions.placeholderOf(R.drawable.actor)).into(castHolder.castImage);
        castHolder.castName.setText(cast.getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class CastHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cast_image)
        AppCompatImageView castImage;
        @BindView(R.id.cast_name)
        AppCompatTextView castName;

         CastHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
