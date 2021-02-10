package com.animsh.moviem.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.repositories.MostPopularMoviesRepository;
import com.animsh.moviem.response.CommonMoviesResponse;

/**
 * Created by animsh on 2/10/2021.
 */
public class MostPopularMoviesViewModel extends ViewModel {

    private MostPopularMoviesRepository mostPopularMoviesRepository;

    public MostPopularMoviesViewModel() {
        mostPopularMoviesRepository = new MostPopularMoviesRepository();
    }

    public LiveData<CommonMoviesResponse> getMostPopularMovies(int page, String apiKey) {
        return mostPopularMoviesRepository.getMostPopularMovies(page, apiKey);
    }
}
