package com.animsh.moviem.viewmodels.tvshowsviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.repositories.tvshowsrespositories.TVOnAirRepository;
import com.animsh.moviem.response.tvshowzresponse.TvShowResponse;

/**
 * Created by animsh on 2/12/2021.
 */
public class TVOnAirViewModel extends ViewModel {

    private TVOnAirRepository tvOnAirRepository;

    public TVOnAirViewModel() {
        tvOnAirRepository = new TVOnAirRepository();
    }

    public LiveData<TvShowResponse> getTVOnAir(int page, String apiKey) {
        return tvOnAirRepository.getTvOnAir(page, apiKey);
    }
}
