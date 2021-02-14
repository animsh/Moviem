package com.animsh.moviem.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.animsh.moviem.R;
import com.animsh.moviem.adapters.MoviesAdapter;
import com.animsh.moviem.databinding.FragmentSimilarMoviesBinding;
import com.animsh.moviem.listeners.MovieListener;
import com.animsh.moviem.response.moviesresponse.MovieResult;
import com.animsh.moviem.utilities.GridSpacingItemDecoration;
import com.animsh.moviem.viewmodels.moviesviewmodel.SimilarMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class SimilarMoviesFragment extends Fragment implements MovieListener {

    int spanCount = 3; // 3 columns
    int spacing = 30; // 50px
    boolean includeEdge = true;
    private FragmentSimilarMoviesBinding fragmentSimilarMoviesBinding;
    private SimilarMoviesViewModel similarMoviesViewModel;
    private List<MovieResult> movieResults = new ArrayList<>();
    private MoviesAdapter moviesAdapter;
    private int movieId = -1;

    public SimilarMoviesFragment(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        similarMoviesViewModel = new ViewModelProvider(this).get(SimilarMoviesViewModel.class);

        moviesAdapter = new MoviesAdapter(movieResults, this);
        fragmentSimilarMoviesBinding.recyclerView.setHasFixedSize(true);
        fragmentSimilarMoviesBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        fragmentSimilarMoviesBinding.recyclerView.setAdapter(moviesAdapter);
        fragmentSimilarMoviesBinding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        getSimilarMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSimilarMoviesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_similar_movies, container, false);
        View view = fragmentSimilarMoviesBinding.getRoot();
        return view;
    }

    public void getSimilarMovies() {
        similarMoviesViewModel.getSimilarMovies(movieId, 1, getString(R.string.api_key)).observe(getViewLifecycleOwner(), similarMoviesResponse -> {
            if (similarMoviesResponse != null) {
                if (similarMoviesResponse.getResults() != null) {
                    movieResults.clear();
                    movieResults.addAll(similarMoviesResponse.getResults());
                    moviesAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onMovieClicked(MovieResult movie) {
        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        intent.putExtra("movieId", movie.getId());
        getContext().startActivity(intent);
    }
}