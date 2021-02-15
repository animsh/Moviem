package com.animsh.moviem.viewmodels.moviesviewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.repositories.moviesrepositories.MovieCreditsRepository;
import com.animsh.moviem.response.CreditsResponse;

/**
 * Created by animsh on 2/15/2021.
 */
public class MovieCreditsViewModel extends ViewModel {

    private MovieCreditsRepository movieCreditsRepository;

    public MovieCreditsViewModel() {
        movieCreditsRepository = new MovieCreditsRepository();
    }

    public MutableLiveData<CreditsResponse> getMovieCredits(int movieId, String apiKey) {
        return movieCreditsRepository.getMovieCredits(movieId, apiKey);
    }
}
