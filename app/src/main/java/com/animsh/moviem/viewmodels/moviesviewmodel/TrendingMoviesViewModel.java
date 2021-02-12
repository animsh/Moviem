package com.animsh.moviem.viewmodels.moviesviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.repositories.moviesrepositories.TrendingMoviesRepository;
import com.animsh.moviem.response.moviesresponse.CommonMoviesResponse;

/**
 * Created by animsh on 2/10/2021.
 */
public class TrendingMoviesViewModel extends ViewModel {

    private TrendingMoviesRepository trendingMoviesRepository;

    public TrendingMoviesViewModel() {
        trendingMoviesRepository = new TrendingMoviesRepository();
    }

    public LiveData<CommonMoviesResponse> getTrendingMovies(int page, String apiKey) {
        return trendingMoviesRepository.getTrendingMovies(page, apiKey);
    }
}