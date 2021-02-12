package com.animsh.moviem.viewmodels.tvshowsviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.model.TVShow;
import com.animsh.moviem.repositories.tvshowsrespositories.LatestTvShowRepository;

/**
 * Created by animsh on 2/12/2021.
 */
public class LatestTVShowViewModel extends ViewModel {

    private LatestTvShowRepository latestTvShowRepository;

    public LatestTVShowViewModel() {
        latestTvShowRepository = new LatestTvShowRepository();
    }

    public LiveData<TVShow> getLatestTVShow(String apiKey) {
        return latestTvShowRepository.getLatestTvShow(apiKey);
    }
}
