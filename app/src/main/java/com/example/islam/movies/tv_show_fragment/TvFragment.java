package com.example.islam.movies.tv_show_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islam.movies.R;
import com.example.islam.movies.fragments.FragPagerAdapter;
import com.example.islam.movies.fragments.TvOnTheAir;
import com.example.islam.movies.fragments.TvOnTheAirToday;
import com.example.islam.movies.fragments.TvPopular;
import com.example.islam.movies.fragments.TvTopRated;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvFragment extends Fragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    ArrayList<Fragment> fragmentArrayList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movies_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new TvPopular());
        fragmentArrayList.add(new TvTopRated());
        fragmentArrayList.add(new TvOnTheAir());
        fragmentArrayList.add(new TvOnTheAirToday());
        FragPagerAdapter adapter = new FragPagerAdapter(getChildFragmentManager(), fragmentArrayList);


        viewPager.setAdapter(adapter);
        tabLayout.addTab(tabLayout.newTab().setText("Popular"));
        tabLayout.addTab(tabLayout.newTab().setText("Top Rated"));
        tabLayout.addTab(tabLayout.newTab().setText("On Tv"));
        tabLayout.addTab(tabLayout.newTab().setText("Airing Today"));

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
}
