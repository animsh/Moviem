package com.animsh.moviem.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.repositories.NowPlayingMoviesRepository;
import com.animsh.moviem.response.UniqueMovieResponse;

/**
 * Created by animsh on 2/10/2021.
 */
public class NowPlayingMoviesViewModel extends ViewModel {

    private NowPlayingMoviesRepository nowPlayingMoviesRepository;

    public NowPlayingMoviesViewModel() {
        nowPlayingMoviesRepository = new NowPlayingMoviesRepository();
    }

    public LiveData<UniqueMovieResponse> getNowPlayingMovies(int page, String apiKey) {
        return nowPlayingMoviesRepository.getNowPlayingMovies(page, apiKey);
    }

}
