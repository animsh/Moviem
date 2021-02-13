package com.animsh.moviem.viewmodels.moviesviewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.model.Movie;
import com.animsh.moviem.repositories.moviesrepositories.MovieDetailsRepository;

/**
 * Created by animsh on 2/13/2021.
 */
public class MovieDetailsVieModel extends ViewModel {

    private MovieDetailsRepository movieDetailsRepository;

    public MovieDetailsVieModel() {
        movieDetailsRepository = new MovieDetailsRepository();
    }

    public MutableLiveData<Movie> getMovieDetails(int movieId, String apiKey) {
        return movieDetailsRepository.getMovieDetails(movieId, apiKey);
    }
}
