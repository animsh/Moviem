package com.animsh.moviem.viewmodels.tvshowsviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.repositories.tvshowsrespositories.TrendingTVShowRepository;
import com.animsh.moviem.response.tvshowzresponse.TvShowResponse;

/**
 * Created by animsh on 2/12/2021.
 */
public class TrendingTVShowViewModel extends ViewModel {

    private TrendingTVShowRepository trendingTVShowRepository;

    public TrendingTVShowViewModel() {
        trendingTVShowRepository = new TrendingTVShowRepository();
    }

    public LiveData<TvShowResponse> getTrendingTVShow(int page, String apiKey) {
        return trendingTVShowRepository.getTrendingTVShows(page, apiKey);
    }
}
