package com.animsh.moviem.activities.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.animsh.moviem.R;
import com.animsh.moviem.activities.TVShowDetailsActivity;
import com.animsh.moviem.adapters.TVAdapter;
import com.animsh.moviem.adapters.TrendingTVAdapter;
import com.animsh.moviem.databinding.FragmentTvBinding;
import com.animsh.moviem.listeners.TvListener;
import com.animsh.moviem.response.tvshowzresponse.TVShowResult;
import com.animsh.moviem.viewmodels.tvshowsviewmodel.LatestTVShowViewModel;
import com.animsh.moviem.viewmodels.tvshowsviewmodel.PopularTVShowsViewModel;
import com.animsh.moviem.viewmodels.tvshowsviewmodel.TVAiringTodayViewModel;
import com.animsh.moviem.viewmodels.tvshowsviewmodel.TVOnAirViewModel;
import com.animsh.moviem.viewmodels.tvshowsviewmodel.TopRatedTVShowViewModel;
import com.animsh.moviem.viewmodels.tvshowsviewmodel.TrendingTVShowViewModel;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TvFragment extends Fragment implements TvListener {

    public boolean isRevert = false;
    private FragmentTvBinding fragmentTvBinding;
    private TrendingTVShowViewModel trendingTVShowViewModel;
    private PopularTVShowsViewModel popularTVShowsViewModel;
    private TopRatedTVShowViewModel topRatedTVShowViewModel;
    private LatestTVShowViewModel latestTVShowViewModel;
    private TVAiringTodayViewModel tvAiringTodayViewModel;
    private TVOnAirViewModel tvOnAirViewModel;
    private List<TVShowResult> tvOnAir = new ArrayList<>();
    private List<TVShowResult> tvAiringToday = new ArrayList<>();
    private List<TVShowResult> popularTVShows = new ArrayList<>();
    private List<TVShowResult> topRatedTVShows = new ArrayList<>();
    private List<TVShowResult> trendingTVShows = new ArrayList<>();
    private Handler sliderHandler = new Handler();
    private TrendingTVAdapter trendingTVAdapter;
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRevert) {
                fragmentTvBinding.trendingTVShowsViewPager.setCurrentItem(fragmentTvBinding.trendingTVShowsViewPager.getCurrentItem() - 1, true);
                if (fragmentTvBinding.trendingTVShowsViewPager.getCurrentItem() == 0) {
                    isRevert = false;
                }
            } else {
                fragmentTvBinding.trendingTVShowsViewPager.setCurrentItem(fragmentTvBinding.trendingTVShowsViewPager.getCurrentItem() + 1, true);
                if (fragmentTvBinding.trendingTVShowsViewPager.getCurrentItem() == 19) {
                    isRevert = true;
                }
            }
        }
    };
    private TVAdapter popularTVAdapter, topRatedTVAdapter, tvOnAirAdapter, tvAiringTodayAdapter;

    public TvFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                initializeComponents();
                getLatestTVShows();
                getTrendingTVShows();
                getPopularTVShows();
                getTopRatedTVShows();
                getTVAiringToday();
                getTVOnAir();
            }
        });

        fragmentTvBinding.tvShowsRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.post(new Runnable() {
                    public void run() {
                        fragmentTvBinding.trendingTVShowsViewPager.setCurrentItem(0, true);
                        getLatestTVShows();
                        getTrendingTVShows();
                        getPopularTVShows();
                        getTopRatedTVShows();
                        getTVAiringToday();
                        getTVOnAir();
                        fragmentTvBinding.tvShowsRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    public void initializeComponents() {
        trendingTVShowViewModel = new ViewModelProvider(this).get(TrendingTVShowViewModel.class);
        popularTVShowsViewModel = new ViewModelProvider(this).get(PopularTVShowsViewModel.class);
        topRatedTVShowViewModel = new ViewModelProvider(this).get(TopRatedTVShowViewModel.class);
        latestTVShowViewModel = new ViewModelProvider(this).get(LatestTVShowViewModel.class);
        tvAiringTodayViewModel = new ViewModelProvider(this).get(TVAiringTodayViewModel.class);
        tvOnAirViewModel = new ViewModelProvider(this).get(TVOnAirViewModel.class);

        trendingTVAdapter = new TrendingTVAdapter(trendingTVShows, this);
        fragmentTvBinding.trendingTVShowsViewPager.setAdapter(trendingTVAdapter);
        fragmentTvBinding.trendingTVShowsViewPager.setClipToPadding(false);
        fragmentTvBinding.trendingTVShowsViewPager.setClipChildren(false);
        fragmentTvBinding.trendingTVShowsViewPager.setOffscreenPageLimit(3);
        fragmentTvBinding.trendingTVShowsViewPager.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        fragmentTvBinding.trendingTVShowsViewPager.setPageTransformer(compositePageTransformer);
        fragmentTvBinding.trendingTVShowsViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });

        popularTVAdapter = new TVAdapter(popularTVShows, this);
        fragmentTvBinding.popularTVShowsRV.setHasFixedSize(true);
        fragmentTvBinding.popularTVShowsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        fragmentTvBinding.popularTVShowsRV.setAdapter(popularTVAdapter);

        topRatedTVAdapter = new TVAdapter(topRatedTVShows, this);
        fragmentTvBinding.topRatedTVShowsRV.setHasFixedSize(true);
        fragmentTvBinding.topRatedTVShowsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        fragmentTvBinding.topRatedTVShowsRV.setAdapter(topRatedTVAdapter);

        tvAiringTodayAdapter = new TVAdapter(tvAiringToday, this);
        fragmentTvBinding.tvAiringTodayRV.setHasFixedSize(true);
        fragmentTvBinding.tvAiringTodayRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        fragmentTvBinding.tvAiringTodayRV.setAdapter(tvAiringTodayAdapter);

        tvOnAirAdapter = new TVAdapter(tvOnAir, this);
        fragmentTvBinding.tvOnAirRV.setHasFixedSize(true);
        fragmentTvBinding.tvOnAirRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        fragmentTvBinding.tvOnAirRV.setAdapter(tvOnAirAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTvBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv, container, false);
        View view = fragmentTvBinding.getRoot();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 1000);
    }

    public void getTrendingTVShows() {
        trendingTVShowViewModel.getTrendingTVShow(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), trendingTVShowsResponse ->
        {
            if (trendingTVShowsResponse != null) {
                if (trendingTVShowsResponse.getResults() != null) {
                    trendingTVShows.clear();
                    trendingTVShows.addAll(trendingTVShowsResponse.getResults());
                    trendingTVAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void getTVOnAir() {
        tvOnAirViewModel.getTVOnAir(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), tvOnAirResponse -> {
            if (tvOnAirResponse != null) {
                if (tvOnAirResponse.getResults() != null) {
                    tvOnAir.clear();
                    tvOnAir.addAll(tvOnAirResponse.getResults());
                    tvOnAirAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void getTVAiringToday() {
        tvAiringTodayViewModel.getTVAiringToday(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), tvAiringTodayResponse -> {
            if (tvAiringTodayResponse != null) {
                if (tvAiringTodayResponse.getResults() != null) {
                    tvAiringToday.clear();
                    tvAiringToday.addAll(tvAiringTodayResponse.getResults());
                    tvAiringTodayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void getPopularTVShows() {
        popularTVShowsViewModel.getPopularTVShows(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), popularTVShowsResponse -> {
            if (popularTVShowsResponse != null) {
                if (popularTVShowsResponse.getResults() != null) {
                    popularTVShows.clear();
                    popularTVShows.addAll(popularTVShowsResponse.getResults());
                    popularTVAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void getTopRatedTVShows() {
        topRatedTVShowViewModel.getTopRatedTVShows(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), topRatedTVShowsResponse -> {
            if (topRatedTVShowsResponse != null) {
                if (topRatedTVShowsResponse.getResults() != null) {
                    topRatedTVShows.clear();
                    topRatedTVShows.addAll(topRatedTVShowsResponse.getResults());
                    topRatedTVAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void getLatestTVShows() {
        latestTVShowViewModel.getLatestTVShow(getString(R.string.api_key)).observe(getViewLifecycleOwner(), latestTVShowsResponse -> {
            Picasso.get().load(latestTVShowsResponse.getPoster_path()).noFade().placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(fragmentTvBinding.thumbnail);
            fragmentTvBinding.latestTVShowTitle.setText(latestTVShowsResponse.getName());
            fragmentTvBinding.latestTVShowStatus.setText(latestTVShowsResponse.getStatus());
            fragmentTvBinding.latestTVShowSeasons.setText(String.valueOf(latestTVShowsResponse.getNumber_of_seasons()));
            fragmentTvBinding.latestTVShowEpisodes.setText(String.valueOf(latestTVShowsResponse.getNumber_of_episodes()));
            fragmentTvBinding.latestEPRuntime.setText(latestTVShowsResponse.getEpisode_run_time().isEmpty() ? "null" : MessageFormat.format("{0} Min.", latestTVShowsResponse.getEpisode_run_time().get(0)));
            fragmentTvBinding.latestTVShowOverView.setText(latestTVShowsResponse.getOverview().isEmpty() ? "null" : latestTVShowsResponse.getOverview());
        });
    }

    @Override
    public void onTVClicked(TVShowResult tvShowResult) {
        Intent intent = new Intent(getContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvId", tvShowResult.getId());
        getContext().startActivity(intent);
    }
}