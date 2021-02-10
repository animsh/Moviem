package com.animsh.moviem.activities.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.animsh.moviem.R;
import com.animsh.moviem.viewmodels.LatestMovieViewModel;
import com.animsh.moviem.viewmodels.MostPopularMoviesViewModel;
import com.animsh.moviem.viewmodels.NowPlayingMoviesViewModel;
import com.animsh.moviem.viewmodels.TopRatedMoviesViewModel;
import com.animsh.moviem.viewmodels.TrendingMoviesViewModel;

public class MovieFragment extends Fragment {

    private TrendingMoviesViewModel trendingMoviesViewModel;
    private MostPopularMoviesViewModel mostPopularMoviesViewModel;
    private TopRatedMoviesViewModel topRatedMoviesViewModel;
    private NowPlayingMoviesViewModel nowPlayingMoviesViewModel;
    private LatestMovieViewModel latestMovieViewModel;

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
        trendingMoviesViewModel = new ViewModelProvider(this).get(TrendingMoviesViewModel.class);
        mostPopularMoviesViewModel = new ViewModelProvider(this).get(MostPopularMoviesViewModel.class);
        topRatedMoviesViewModel = new ViewModelProvider(this).get(TopRatedMoviesViewModel.class);
        nowPlayingMoviesViewModel = new ViewModelProvider(this).get(NowPlayingMoviesViewModel.class);
        latestMovieViewModel = new ViewModelProvider(this).get(LatestMovieViewModel.class);
        getTrendingMovies();
        getMostPopularMovie();
        getTopRatedMovies();
        getNowPlayingMovies();
        getLatestMovie();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    private void getTrendingMovies() {
        trendingMoviesViewModel.getTrendingMovies(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), trendingMoviesResponse ->
                Toast.makeText(
                        getContext()
                        , "Total Pages Trending: " + trendingMoviesResponse.getTotal_pages()
                        , Toast.LENGTH_SHORT).show()
        );
    }

    private void getMostPopularMovie() {
        mostPopularMoviesViewModel.getMostPopularMovies(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), mostPopularMovieResponse ->
                Toast.makeText(
                        getContext()
                        , "Total Pages Popular: " + mostPopularMovieResponse.getTotal_pages()
                        , Toast.LENGTH_SHORT).show()
        );
    }

    private void getTopRatedMovies() {
        topRatedMoviesViewModel.getTopRatedMovies(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), topRatedMoviesResponse ->
                Toast.makeText(
                        getContext()
                        , "Total Pages Top Rated: " + topRatedMoviesResponse.getTotal_pages()
                        , Toast.LENGTH_SHORT).show()
        );
    }

    private void getNowPlayingMovies() {
        nowPlayingMoviesViewModel.getNowPlayingMovies(1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), nowPlayingMoviesResponse ->
                Toast.makeText(
                        getContext()
                        , "Total Pages Now Playing: " + nowPlayingMoviesResponse.getTotal_pages()
                        , Toast.LENGTH_SHORT).show()
        );
    }

    private void getLatestMovie() {
        latestMovieViewModel.getLatestMovie(getString(R.string.api_key)).observe(getViewLifecycleOwner(), latestMovieResponse ->
                Toast.makeText(
                        getContext()
                        , "Latest Movie:" + latestMovieResponse.getTitle()
                        , Toast.LENGTH_SHORT).show());
    }
}