package com.example.islam.movies.watch_later;

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
import com.example.islam.movies.Const;
import com.example.islam.movies.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaterAdapter extends RecyclerView.Adapter<LaterAdapter.LaterHolder> {

    private List<WatchLaterEntity> entityList;
    private Context context;
    private OnLaterClickListener listener;

    LaterAdapter(Context context, List<WatchLaterEntity> entityList) {
        this.context = context;
        this.entityList = entityList;
    }


    interface OnLaterClickListener {
        void onLaterClicked(int position);
    }

    public void setListener(OnLaterClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LaterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LaterHolder(LayoutInflater.from(context).inflate(R.layout.later_item, parent, false)
                , listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LaterHolder holder, int position) {

        WatchLaterEntity entity = entityList.get(position);
        assert entity != null;
        Glide.with(context).load(Const.IMAGE_URL + entity.getItemImage()).apply(RequestOptions.placeholderOf(R.drawable.actor)).into(holder.laterImage);
        holder.laterTitle.setText(entity.getName());
        holder.laterType.setText(entity.getType());


    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    class LaterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.later_image)
        AppCompatImageView laterImage;
        @BindView(R.id.later_title)
        AppCompatTextView laterTitle;
        @BindView(R.id.later_type)
        AppCompatTextView laterType;

        OnLaterClickListener listener;

        LaterHolder(View itemView, OnLaterClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                listener.onLaterClicked(getAdapterPosition());
            }
        }
    }
}
