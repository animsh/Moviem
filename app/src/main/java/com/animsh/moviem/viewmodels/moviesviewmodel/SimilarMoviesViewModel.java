package com.animsh.moviem.viewmodels.moviesviewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.repositories.moviesrepositories.SimilarMoviesRepository;
import com.animsh.moviem.response.moviesresponse.CommonMoviesResponse;

/**
 * Created by animsh on 2/14/2021.
 */
public class SimilarMoviesViewModel extends ViewModel {

    private SimilarMoviesRepository similarMoviesRepository;

    public SimilarMoviesViewModel() {
        similarMoviesRepository = new SimilarMoviesRepository();
    }

    public MutableLiveData<CommonMoviesResponse> getSimilarMovies(int movieId, int page, String apiKey) {
        return similarMoviesRepository.getSimilarMovies(movieId, page, apiKey);
    }
}
