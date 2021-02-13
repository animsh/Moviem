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
import com.animsh.moviem.activities.MovieDetailsActivity;
import com.animsh.moviem.adapters.MoviesAdapter;
import com.animsh.moviem.adapters.TrendingAdapter;
import com.animsh.moviem.databinding.FragmentMovieBinding;
import com.animsh.moviem.listeners.MovieListener;
import com.animsh.moviem.response.moviesresponse.MovieResult;
import com.animsh.moviem.viewmodels.moviesviewmodel.LatestMovieViewModel;
import com.animsh.moviem.viewmodels.moviesviewmodel.MostPopularMoviesViewModel;
import com.animsh.moviem.viewmodels.moviesviewmodel.NowPlayingMoviesViewModel;
import com.animsh.moviem.viewmodels.moviesviewmodel.TopRatedMoviesViewModel;
import com.animsh.moviem.viewmodels.moviesviewmodel.TrendingMoviesViewModel;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment implements MovieListener {

    public boolean isRevert = false;
    private FragmentMovieBinding fragmentMovieBinding;
    private TrendingMoviesViewModel trendingMoviesViewModel;
    private MostPopularMoviesViewModel mostPopularMoviesViewModel;
    private TopRatedMoviesViewModel topRatedMoviesViewModel;
    private NowPlayingMoviesViewModel nowPlayingMoviesViewModel;
    private LatestMovieViewModel latestMovieViewModel;
    private List<MovieResult> nowPlayingMovies = new ArrayList<>();
    private List<MovieResult> popularMovies = new ArrayList<>();
    private List<MovieResult> topRatedMovies = new ArrayList<>();
    private List<MovieResult> trendingMovies = new ArrayList<>();
    private MoviesAdapter nowPlayingAdapter, popularAdapter, topRatedAdapter;
    private TrendingAdapter trendingMoviesAdapter;
    private Handler sliderHandler = new Handler();

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRevert) {
                fragmentMovieBinding.trendingMoviesViewPager.setCurrentItem(fragmentMovieBinding.trendingMoviesViewPager.getCurrentItem() - 1, true);
                if (fragmentMovieBinding.trendingMoviesViewPager.getCurrentItem() == 0) {
                    isRevert = false;
                }
            } else {
                fragmentMovieBinding.trendingMoviesViewPager.setCurrentItem(fragmentMovieBinding.trendingMoviesViewPager.getCurrentItem() + 1, true);
                if (fragmentMovieBinding.trendingMoviesViewPager.getCurrentItem() == 19) {
                    isRevert = true;
                }
            }
        }
    };

    public MovieFragment() {
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
        handler.postDelayed(new Runnable() {
            public void run() {
                doInitialization();
                getLatestMovie();
                getTrendingMovies();
                getMostPopularMovie();
                getTopRatedMovies();
                getNowPlayingMovies();
            }
        }, 1000);

        fragmentMovieBinding.movieRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.post(new Runnable() {
                    public void run() {
                        fragmentMovieBinding.trendingMoviesViewPager.setCurrentItem(0, true);
                        getLatestMovie();
                        getTrendingMovies();
                        getMostPopularMovie();
                        getTopRatedMovies();
                        getNowPlayingMovies();

                        fragmentMovieBinding.movieRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMovieBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);
        View view = fragmentMovieBinding.getRoot();
        return view;
    }

    private void doInitialization() {
        trendingMoviesViewModel = new ViewModelProvider(this).get(TrendingMoviesViewModel.class);
        mostPopularMoviesViewModel = new ViewModelProvider(this).get(MostPopularMoviesViewModel.class);
        topRatedMoviesViewModel = new ViewModelProvider(this).get(TopRatedMoviesViewModel.class);
        nowPlayingMoviesViewModel = new ViewModelProvider(this).get(NowPlayingMoviesViewModel.class);
        latestMovieViewModel = new ViewModelProvider(this).get(LatestMovieViewModel.class);

        trendingMoviesAdapter = new TrendingAdapter(trendingMovies, this);
        fragmentMovieBinding.trendingMoviesViewPager.setAdapter(trendingMoviesAdapter);
        fragmentMovieBinding.trendingMoviesViewPager.setClipToPadding(false);
        fragmentMovieBinding.trendingMoviesViewPager.setClipChildren(false);
        fragmentMovieBinding.trendingMoviesViewPager.setOffscreenPageLimit(3);
        fragmentMovieBinding.trendingMoviesViewPager.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        fragmentMovieBinding.trendingMoviesViewPager.setPageTransformer(compositePageTransformer);
        fragmentMovieBinding.trendingMoviesViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });

        nowPlayingAdapter = new MoviesAdapter(nowPlayingMovies, this);
        fragmentMovieBinding.nowPlayingMoviesRV.setHasFixedSize(true);
        fragmentMovieBinding.nowPlayingMoviesRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        fragmentMovieBinding.nowPlayingMoviesRV.setAdapter(nowPlayingAdapter);

        popularAdapter = new MoviesAdapter(popularMovies, this);
        fragmentMovieBinding.popularMoviesRV.setHasFixedSize(true);
        fragmentMovieBinding.popularMoviesRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        fragmentMovieBinding.popularMoviesRV.setAdapter(popularAdapter);

        topRatedAdapter = new MoviesAdapter(topRatedMovies, this);
        fragmentMovieBinding.topRatedMoviesRV.setHasFixedSize(true);
        fragmentMovieBinding.topRatedMoviesRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        fragmentMovieBinding.topRatedMoviesRV.setAdapter(topRatedAdapter);
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

    private void getTrendingMovies() {
        trendingMoviesViewModel.getTrendingMovies(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), trendingMoviesResponse -> {
            if (trendingMoviesResponse != null) {
                if (trendingMoviesResponse.getResults() != null) {
                    trendingMovies.clear();
                    trendingMovies.addAll(trendingMoviesResponse.getResults());
                    trendingMoviesAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getMostPopularMovie() {
        mostPopularMoviesViewModel.getMostPopularMovies(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), mostPopularMovieResponse -> {
            if (mostPopularMovieResponse != null) {
                if (mostPopularMovieResponse.getResults() != null) {
                    popularMovies.clear();
                    popularMovies.addAll(mostPopularMovieResponse.getResults());
                    popularAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getTopRatedMovies() {
        topRatedMoviesViewModel.getTopRatedMovies(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), topRatedMoviesResponse -> {
            if (topRatedMoviesResponse != null) {
                if (topRatedMoviesResponse.getResults() != null) {
                    topRatedMovies.clear();
                    topRatedMovies.addAll(topRatedMoviesResponse.getResults());
                    topRatedAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getNowPlayingMovies() {
        nowPlayingMoviesViewModel.getNowPlayingMovies(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), nowPlayingMoviesResponse -> {
            if (nowPlayingMoviesResponse != null) {
                if (nowPlayingMoviesResponse.getResults() != null) {
                    nowPlayingMovies.clear();
                    nowPlayingMovies.addAll(nowPlayingMoviesResponse.getResults());
                    nowPlayingAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getLatestMovie() {
        latestMovieViewModel.getLatestMovie(getString(R.string.api_key)).observe(getViewLifecycleOwner(), latestMovieResponse -> {
            Picasso.get().load(latestMovieResponse.getPoster_path()).noFade().placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(fragmentMovieBinding.thumbnail);
            fragmentMovieBinding.latestMovieTitle.setText(latestMovieResponse.getTitle());
            fragmentMovieBinding.latestMovieStatus.setText(latestMovieResponse.getStatus());
            fragmentMovieBinding.latestMovieLanguage.setText(latestMovieResponse.getOriginal_language().toUpperCase());
            fragmentMovieBinding.latestMovieRuntime.setText(MessageFormat.format("{0} Min.", latestMovieResponse.getRuntime()));
            fragmentMovieBinding.latestMovieOverView.setText(latestMovieResponse.getOverview().isEmpty() ? "null" : latestMovieResponse.getOverview());
            fragmentMovieBinding.latestMovieAdult.setText(latestMovieResponse.isAdult() ? "Yes" : "No");
        });
    }

    @Override
    public void onMovieClicked(MovieResult movie) {
        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        intent.putExtra("movieId", movie.getId());
        getContext().startActivity(intent);
    }
}