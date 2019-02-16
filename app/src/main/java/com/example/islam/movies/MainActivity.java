package com.example.islam.movies;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.islam.movies.fragments.FragPagerAdapter;
import com.example.islam.movies.fragments.NowPlaying;
import com.example.islam.movies.fragments.Popular;
import com.example.islam.movies.fragments.TopRated;
import com.example.islam.movies.fragments.UpComing;
import com.example.islam.movies.model.Movie;
import com.example.islam.movies.model.MoviesResponse;
import com.example.islam.movies.model.TvResponse;
import com.example.islam.movies.moviedetails.MovieDetails;
import com.example.islam.movies.search_mvp.SearchAdapter;
import com.example.islam.movies.search_mvp.SearchPresenter;
import com.example.islam.movies.search_mvp.SearchPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements com.example.islam.movies.search_mvp.SearchView, SearchAdapter.OnSearchMovieClickListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.search_recycler)
    RecyclerView searchrecycler;
    ArrayList<Fragment> fragmentArrayList;

    List<Movie> searchMovieList;
    SearchAdapter searchAdapter;
    SearchPresenter presenter;

    @BindView(R.id.linear)
    LinearLayout layout;


    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new SearchPresenterImpl(this);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.drawable.ic_film_reel);


//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new Popular());
        fragmentArrayList.add(new TopRated());
        fragmentArrayList.add(new UpComing());
        fragmentArrayList.add(new NowPlaying());
        FragPagerAdapter adapter = new FragPagerAdapter(getSupportFragmentManager(), fragmentArrayList);


        viewPager.setAdapter(adapter);
        tabLayout.addTab(tabLayout.newTab().setText("Popular"));
        tabLayout.addTab(tabLayout.newTab().setText("Top Rated"));
        tabLayout.addTab(tabLayout.newTab().setText("Up Coming"));
        tabLayout.addTab(tabLayout.newTab().setText("Now Playing"));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.details, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!query.isEmpty()) {
                    presenter.getSearchMovies(query, 1);
                } else {
                    if (layout.getVisibility() == View.INVISIBLE) {
                        searchrecycler.setVisibility(View.INVISIBLE);
                        layout.setVisibility(View.VISIBLE);

                        searchMovieList = new ArrayList<>();
                    }


                }

//                if (query.isEmpty()) {
//                    //  searchrecycler.setVisibility(View.INVISIBLE);
//                    layout.setVisibility(View.VISIBLE);
//                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("size", "Text new " + newText);
                if (!newText.isEmpty()) {
                    presenter.getSearchMovies(newText, 1);
                } else {
                    if (layout.getVisibility() == View.INVISIBLE) {
                        searchrecycler.setVisibility(View.INVISIBLE);
                        layout.setVisibility(View.VISIBLE);
                        searchMovieList = new ArrayList<>();
                    }
                }
//                if (newText.isEmpty() ){
//                    //  searchrecycler.setVisibility(View.INVISIBLE);
//                    layout.setVisibility(View.VISIBLE);
//                }


                return false;
            }
        });

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
    public void getSearchedMovies(MoviesResponse response) {
        //if (response.getResults().size() == 0 || response.getResults() == null) {


        searchMovieList = new ArrayList<>();

        searchMovieList.clear();
        searchrecycler.setVisibility(View.VISIBLE);
        searchMovieList = response.getResults();

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchrecycler.setLayoutManager(manager);
        searchAdapter = new SearchAdapter(this, 1);

        searchAdapter.setOnSearchMovieClickListener(this);
        layout.setVisibility(View.INVISIBLE);
        searchrecycler.setAdapter(searchAdapter);


        //}


    }

    @Override
    public void getSearchedTv(TvResponse response) {

    }

    @Override
    public void showSearchProgress() {

    }

    @Override
    public void hideSearchProgress() {

    }

    @Override
    public void onSearchMovieClicked(int position) {
        startActivity(new Intent(MainActivity.this, MovieDetails.class)
                .putExtra("id", searchMovieList.get(position)));
    }
}
