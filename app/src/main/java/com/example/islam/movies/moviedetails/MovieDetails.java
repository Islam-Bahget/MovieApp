package com.example.islam.movies.moviedetails;

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
import android.view.Menu;
import android.view.MenuItem;
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

public class MovieDetails extends AppCompatActivity implements ItemDetailsView, View.OnClickListener {

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
    @BindView(R.id.fav)
    AppCompatImageView favourite;

    ItemDetailsPresenter presenter;
    private MovieDetailsResponse detailsResponse;
    private int id;
    private boolean loved;
    WatchLaterEntity later;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        later = new WatchLaterEntity();
        id = getIntent().getIntExtra("id", 0);
        new CompositeDisposable().add(DataBaseSingleton.laterDao(this).getLater(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WatchLaterEntity>() {
                    @Override
                    public void onSuccess(WatchLaterEntity entity) {


                        loved = true;
                        favourite.setImageResource(R.drawable.fav_after);


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


        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;

        toolbar.setNavigationIcon(R.drawable.back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        presenter = new ItemDetailsPresenterImpl(id, this);
        presenter.getMovieDetails(Const.MOVIES);
        presenter.getTrailers(Const.MOVIES);

        favourite.setOnClickListener(this);
    }

    @Override
    public void showProgress() {

        if (progressBar.getVisibility() == View.INVISIBLE)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showMovieData(MovieDetailsResponse response) {
        this.detailsResponse = response;
        refreshLayout.setRefreshing(false);
        CastAdapter adapter = new CastAdapter(this, response.getCredits().getCast());
        castRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        castRecycler.setAdapter(adapter);
        genreRecycler.setLayoutManager(new GridLayoutManager(this, 2));

        GenreAdapter genreAdapter = new GenreAdapter(this, response.getGenres());
        genreRecycler.setAdapter(genreAdapter);

        Glide.with(this).load(Const.IMAGE_URL + response.getBackdropPath()).into(movieImage);
        movieTitle.setText(response.getTitle());
        movieReviews.setText(String.valueOf(response.getVote_count()) + " Reviews");
        movieOverView.setText(response.getOverview());
        movieYear.setText(response.getReleaseDate().subSequence(0, 4));
        movieTime.setText(response.getRunTime() + " MIN");
        movieRate.setText(String.valueOf(response.getVoteAverage()));


    }

    @Override
    public void showTvData(TvDetailsResponse response) {

    }

    @Override
    public void showError(String error) {

        if (error.contains("timeout"))
            Toast.makeText(this, "Check Your Internet And try", Toast.LENGTH_SHORT).show();

        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showTrailers(VideoResponse response) {
        playerView.initialize(youTubePlayer -> youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady() {
                youTubePlayer.loadVideo(response.getVideos().get(0).getKey(), 0);


            }
        }), true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        playerView.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        LaterDao dao = DataBaseSingleton.laterDao(this);

        WatchLaterEntity entity = new WatchLaterEntity(id, Const.MOVIE_TYPE, detailsResponse.getTitle(), detailsResponse.getBackdropPath());
        if (!loved) {
            loved = true;
            favourite.setImageResource(R.drawable.fav_after);
            dao.insertLater(entity);

            Toast.makeText(this, "Added To Favourite", Toast.LENGTH_SHORT).show();

        } else {
            loved = false;
            favourite.setImageResource(R.drawable.fav);
            dao.deleteLater(entity);
            Log.d("size", "Item Deleted From Room");
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (loved) {

        }
    }
}
