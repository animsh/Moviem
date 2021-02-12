package com.animsh.moviem.viewmodels.tvshowsviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.repositories.tvshowsrespositories.TVAiringTodayRepository;
import com.animsh.moviem.response.tvshowzresponse.TvShowResponse;

/**
 * Created by animsh on 2/12/2021.
 */
public class TVAiringTodayViewModel extends ViewModel {

    private TVAiringTodayRepository tvAiringTodayRepository;

    public TVAiringTodayViewModel() {
        tvAiringTodayRepository = new TVAiringTodayRepository();
    }

    public LiveData<TvShowResponse> getTVAiringToday(int page, String apiKey) {
        return tvAiringTodayRepository.getTVAiringToday(page, apiKey);
    }
}
