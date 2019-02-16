package com.example.islam.movies.tv_details;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.islam.movies.Const;
import com.example.islam.movies.R;
import com.example.islam.movies.item_details_mvp.CastAdapter;
import com.example.islam.movies.item_details_mvp.GenreAdapter;
import com.example.islam.movies.item_details_mvp.ItemDetailsPresenter;
import com.example.islam.movies.item_details_mvp.ItemDetailsPresenterImpl;
import com.example.islam.movies.item_details_mvp.ItemDetailsView;
import com.example.islam.movies.model.MovieDetailsResponse;
import com.example.islam.movies.model.TvDetailsResponse;
import com.example.islam.movies.model.VideoResponse;
import com.example.islam.movies.watch_later.DataBaseSingleton;
import com.example.islam.movies.watch_later.LaterDao;
import com.example.islam.movies.watch_later.WatchLaterEntity;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TvDetails extends AppCompatActivity implements ItemDetailsView, View.OnClickListener {

    @BindView(R.id.details_movie_image)
    AppCompatImageView movieImage;
    @BindView(R.id.details_movie_overview)
    AppCompatTextView movieOverView;
    @BindView(R.id.details_movie_rate)
    AppCompatTextView movieRate;
    @BindView(R.id.details_movie_time)
    AppCompatTextView movieTime;
    @BindView(R.id.details_movie_reviews)
    AppCompatTextView movieReviews;
    @BindView(R.id.details_movie_title)
    AppCompatTextView movieTitle;
    @BindView(R.id.details_movie_year)
    AppCompatTextView movieYear;
    @BindView(R.id.cast_recyclerview)
    RecyclerView castRecycler;
    @BindView(R.id.details_progress)
    ProgressBar progressBar;
    @BindView(R.id.details_toolbar)
    Toolbar toolbar;
    @BindView(R.id.linear)
    LinearLayout linearLayout;
    @BindView(R.id.genre_recycler)
    RecyclerView genreRecycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.player_view)
    YouTubePlayerView playerView;
    @BindView(R.id.season_recyclerview)
    RecyclerView seasonRecyclerView;
    @BindView(R.id.fav)
    AppCompatImageView fav;

    private TvDetailsResponse detailsResponse;

    boolean loved = false;
    int id;
    ItemDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_details);
        ButterKnife.bind(this);
        fav.setOnClickListener(this);

        id = getIntent().getIntExtra("id", 0);
        new CompositeDisposable().add(DataBaseSingleton.laterDao(this).getLater(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WatchLaterEntity>() {
                    @Override
                    public void onSuccess(WatchLaterEntity entity) {


                        loved = true;
                        fav.setImageResource(R.drawable.fav_after);


                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));

        refreshLayout.setOnRefreshListener(() -> presenter.getMovieDetails(Const.MOVIES));
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/dos.ttf");

        movieOverView.setTypeface(typeface);
        movieReviews.setTypeface(typeface);
        movieYear.setTypeface(typeface);
        movieTime.setTypeface(typeface);
        movieRate.setTypeface(typeface);
        movieTitle.setTypeface(typeface);


        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;

        toolbar.setNavigationIcon(R.drawable.back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        int id = getIntent().getIntExtra("id", 0);
        presenter = new ItemDetailsPresenterImpl(id, this);
        presenter.getMovieDetails(Const.TV);
        presenter.getTrailers(Const.TV);

    }

    @Override
    public void showProgress() {

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showMovieData(MovieDetailsResponse response) {

    }

    @Override
    public void showTvData(TvDetailsResponse response) {
        refreshLayout.setRefreshing(false);

        this.detailsResponse = response;
        CastAdapter adapter = new CastAdapter(this, response.getCredits().getCast());
        castRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        castRecycler.setAdapter(adapter);
        genreRecycler.setLayoutManager(new GridLayoutManager(this, 2));

        GenreAdapter genreAdapter = new GenreAdapter(this, response.getGenres());
        genreRecycler.setAdapter(genreAdapter);
        seasonRecyclerView.setLayoutManager(
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false));

        SeasonsAdapter seasonsAdapter = new SeasonsAdapter(this, response.getSeasons());
        seasonRecyclerView.setAdapter(seasonsAdapter);

        Glide.with(this).load(Const.IMAGE_URL + response.getBackDopPath()).into(movieImage);
        movieTitle.setText(response.getName());
        movieReviews.setText(String.valueOf(response.getVoteCount()) + " Reviews");
        movieOverView.setText(response.getOverView());
        movieYear.setText(response.getFirstAirDate().subSequence(0, 4));

        movieRate.setText(String.valueOf(response.getVoteAverage()));
    }

    @Override
    public void showTrailers(VideoResponse response) {
        if (response.getVideos().size() > 0)
            playerView.initialize(youTubePlayer -> youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady() {
                    youTubePlayer.loadVideo(response.getVideos().get(0).getKey(), 0);


                }
            }), true);

    }

    @Override
    public void showError(String error) {

        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        LaterDao dao = DataBaseSingleton.laterDao(this);

        WatchLaterEntity entity = new WatchLaterEntity(id, Const.TV_TYPE, detailsResponse.getName(), detailsResponse.getBackDopPath());
        if (loved) {
            loved = false;
            fav.setImageResource(R.drawable.fav);
            dao.deleteLater(entity);

            Toast.makeText(this, "Added To Favourite", Toast.LENGTH_SHORT).show();

        } else {
            loved = true;
            fav.setImageResource(R.drawable.fav_after);
            dao.insertLater(entity);
            Log.d("size", "Item Deleted From Room");
        }

    }
}
