package com.animsh.moviem.viewmodels.moviesviewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.repositories.moviesrepositories.RecommendedMoviesRepository;
import com.animsh.moviem.response.moviesresponse.CommonMoviesResponse;

/**
 * Created by animsh on 2/15/2021.
 */
public class RecommendedMoviesViewModel extends ViewModel {

    private RecommendedMoviesRepository recommendedMoviesRepository;

    public RecommendedMoviesViewModel() {
        recommendedMoviesRepository = new RecommendedMoviesRepository();
    }

    public MutableLiveData<CommonMoviesResponse> getRecommendedMovie(int movieId, int page, String apiKey) {
        return recommendedMoviesRepository.getRecommendedMovies(movieId, page, apiKey);
    }
}
