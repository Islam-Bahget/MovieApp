package com.example.islam.movies;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.islam.movies.model.Movie;
import com.example.islam.movies.model.MoviesResponse;
import com.example.islam.movies.model.TV;
import com.example.islam.movies.model.TvResponse;
import com.example.islam.movies.movie_fragment.MovieFragment;
import com.example.islam.movies.moviedetails.MovieDetails;
import com.example.islam.movies.search_mvp.SearchAdapter;
import com.example.islam.movies.search_mvp.SearchPresenter;
import com.example.islam.movies.search_mvp.SearchPresenterImpl;
import com.example.islam.movies.search_mvp.SearchView;
import com.example.islam.movies.trending_fragment.TrendingFragment;
import com.example.islam.movies.tv_details.TvDetails;
import com.example.islam.movies.tv_show_fragment.TvFragment;
import com.example.islam.movies.watch_later.WatchLaterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements SearchView, SearchAdapter.OnSearchMovieClickListener {


    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout frameLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.search_recycler)
    RecyclerView searchRecycler;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    List<Movie> searchMovieList;
    List<TV> searchTvList;
    android.support.v7.widget.SearchView searchView;


    public static int currentIndex = 0;

    SearchPresenter presenter;


    private static final String MOVIE_TAG = "movies";
    private static final String TV_TAG = "tv";
    private static final String TRENDING_TAG = "trending";
    private static final String WATCH_TAG = "watch";

    public static String currentTag = MOVIE_TAG;

    private String[] titles = {"Movies", "Tv Shows", "Trending", "Watch Later"};
    private static boolean shouldLoadMovie = true;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        presenter = new SearchPresenterImpl(this);
        handler = new Handler();


        setUpNavigation();
        if (savedInstanceState == null) {
            currentTag = MOVIE_TAG;
            currentIndex = 0;
            loadHomeFragment();
        }


    }

    public void loadHomeFragment() {
        selectItem();
        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(currentTag) != null) {
            drawerLayout.closeDrawers();
        }

        //threads to load fragments
        Runnable runnable = () -> getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, getHomeFragment(), currentTag)
                .commitAllowingStateLoss();

        handler.post(runnable);
        drawerLayout.closeDrawers();
    }

    private void setToolbarTitle() {
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(titles[currentIndex]);
    }

    private void selectItem() {
        navigationView.getMenu().getItem(currentIndex).setCheckable(true);
    }

    private Fragment getHomeFragment() {
        switch (currentIndex) {
            case 0:
                return new MovieFragment();
            case 1:
                return new TvFragment();
            case 2:
                return new TrendingFragment();
            case 3:
                return new WatchLaterFragment();
            default:
                return new MovieFragment();

        }
    }

    private void setUpNavigation() {
        navigationView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.movies:
                    currentIndex = 0;
                    currentTag = MOVIE_TAG;
                    break;
                case R.id.tv:
                    currentIndex = 1;
                    currentTag = TV_TAG;
                    break;
                case R.id.trending:
                    currentIndex = 2;
                    currentTag = TRENDING_TAG;
                    break;
                case R.id.watch_later:
                    currentIndex = 3;
                    currentTag = WATCH_TAG;
                    break;
                default:
                    currentIndex = 0;
                    currentTag = MOVIE_TAG;
            }
            if (searchRecycler.getVisibility() == View.VISIBLE) {
                searchRecycler.setVisibility(View.INVISIBLE);
                frameLayout.setVisibility(View.VISIBLE);
            }
            if (item.isChecked()) {
                item.setChecked(false);
            } else {
                item.setChecked(true);
            }
            item.setChecked(true);
            loadHomeFragment();
            invalidateOptionsMenu();


            return true;
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        if (searchRecycler.getVisibility() == View.VISIBLE) {
            searchRecycler.setVisibility(View.INVISIBLE);
        }
        if (shouldLoadMovie) {
            if (currentIndex != 0) {
                currentIndex = 0;
                currentTag = MOVIE_TAG;
                loadHomeFragment();

                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public void getSearchedMovies(MoviesResponse response) {

        searchMovieList = new ArrayList<>();

        searchMovieList.clear();
        //    searchRecycler.setVisibility(View.VISIBLE);
        searchMovieList = response.getResults();

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchRecycler.setLayoutManager(manager);
        SearchAdapter searchAdapter = new SearchAdapter(this, Const.MOVIES);
        searchAdapter.setMovieList(response.getResults());

        searchAdapter.setOnSearchMovieClickListener(this);

        searchRecycler.setAdapter(searchAdapter);
    }

    @Override
    public void getSearchedTv(TvResponse response) {
        searchTvList = new ArrayList<>();

        searchTvList.clear();
        //   searchRecycler.setVisibility(View.VISIBLE);
        searchTvList = response.getResults();

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchRecycler.setLayoutManager(manager);
        SearchAdapter searchAdapter = new SearchAdapter(this, Const.TV);
        searchAdapter.setTvList(response.getResults());

        searchAdapter.setOnSearchMovieClickListener(this);

        searchRecycler.setAdapter(searchAdapter);
    }

    @Override
    public void showSearchProgress() {

    }

    @Override
    public void hideSearchProgress() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        Log.d("men", "Menu");
        if (currentIndex == 2) {


            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.filter));

            toolbar.getContext().setTheme(R.style.ToolBarTrend);
        }
        if (currentIndex == 0 || currentIndex == 1) {
            getMenuInflater().inflate(R.menu.details, menu);

            toolbar.getContext().setTheme(R.style.ToolBar);


            SearchManager manager = (SearchManager) getSystemService(SEARCH_SERVICE);
            searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.menu_search).getActionView();
            assert manager != null;
            searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    if (!query.isEmpty()) {
                        frameLayout.setVisibility(View.INVISIBLE);
                        if (currentTag.equals(MOVIE_TAG)) {
                            presenter.getSearchMovies(query, Const.MOVIES);

                        } else if (currentTag.equals(TV_TAG)) {
                            presenter.getSearchMovies(query, Const.TV);
                        }
                    } else {
                        if (frameLayout.getVisibility() == View.INVISIBLE) {
                            frameLayout.setVisibility(View.VISIBLE);
                            searchRecycler.setVisibility(View.INVISIBLE);
                            searchMovieList = new ArrayList<>();
                            searchTvList = new ArrayList<>();
                        }
                    }

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    if (!newText.isEmpty()) {
                        frameLayout.setVisibility(View.INVISIBLE);
                        searchRecycler.setVisibility(View.VISIBLE);
                        if (currentTag.equals(MOVIE_TAG)) {
                            presenter.getSearchMovies(newText, Const.MOVIES);

                        } else if (currentTag.equals(TV_TAG)) {
                            presenter.getSearchMovies(newText, Const.TV);

                        }
                    } else {
                        if (frameLayout.getVisibility() == View.INVISIBLE) {
                            frameLayout.setVisibility(View.VISIBLE);
                            searchRecycler.setVisibility(View.INVISIBLE);
                            searchMovieList = new ArrayList<>();
                            searchTvList = new ArrayList<>();
                        }
                    }
                    return false;
                }
            });
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchMovieClicked(int position) {
        if (currentTag.equals(MOVIE_TAG)) {
            startActivity(new Intent(HomeActivity.this, MovieDetails.class)
                    .putExtra("id", searchMovieList.get(position).getId()));

        } else if (currentTag.equals(TV_TAG)) {
            startActivity(new Intent(HomeActivity.this, TvDetails.class)
                    .putExtra("id", searchTvList.get(position).getId()));

        }
    }
}
