package com.animsh.moviem.viewmodels.tvshowsviewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.model.TVShow;
import com.animsh.moviem.repositories.tvshowsrespositories.TVShowDetailsRepository;

/**
 * Created by animsh on 2/13/2021.
 */
public class TVShowDetailsViewModel extends ViewModel {

    private TVShowDetailsRepository tvShowDetailsRepository;

    public TVShowDetailsViewModel() {
        tvShowDetailsRepository = new TVShowDetailsRepository();
    }

    public MutableLiveData<TVShow> getTVShowDetails(int tvId, String apiKey) {
        return tvShowDetailsRepository.getTVShowDetails(tvId, apiKey);
    }
}
