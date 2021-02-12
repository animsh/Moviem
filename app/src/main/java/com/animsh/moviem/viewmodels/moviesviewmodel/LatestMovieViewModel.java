package com.animsh.moviem.viewmodels.moviesviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.model.Movie;
import com.animsh.moviem.repositories.moviesrepositories.LatestMovieRepository;

/**
 * Created by animsh on 2/10/2021.
 */
public class LatestMovieViewModel extends ViewModel {

    private LatestMovieRepository latestMovieRepository;

    public LatestMovieViewModel() {
        latestMovieRepository = new LatestMovieRepository();
    }

    public LiveData<Movie> getLatestMovie(String apiKey) {
        return latestMovieRepository.getLatestMovie(apiKey);
    }
}
