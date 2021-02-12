package com.animsh.moviem.viewmodels.moviesviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.repositories.moviesrepositories.TopRatedMoviesRepository;
import com.animsh.moviem.response.moviesresponse.CommonMoviesResponse;

/**
 * Created by animsh on 2/10/2021.
 */
public class TopRatedMoviesViewModel extends ViewModel {

    private final TopRatedMoviesRepository topRatedMoviesRepository;

    public TopRatedMoviesViewModel() {
        topRatedMoviesRepository = new TopRatedMoviesRepository();
    }

    public LiveData<CommonMoviesResponse> getTopRatedMovies(int page, String apiKey) {
        return topRatedMoviesRepository.getTopRatedMovies(page, apiKey);
    }
}
