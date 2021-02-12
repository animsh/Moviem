package com.animsh.moviem.viewmodels.tvshowsviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.repositories.tvshowsrespositories.PopularTVShowsRepository;
import com.animsh.moviem.response.tvshowzresponse.TvShowResponse;

/**
 * Created by animsh on 2/12/2021.
 */
public class PopularTVShowsViewModel extends ViewModel {

    private PopularTVShowsRepository popularTVShowsRepository;

    public PopularTVShowsViewModel() {
        popularTVShowsRepository = new PopularTVShowsRepository();
    }

    public LiveData<TvShowResponse> getPopularTVShows(int page, String apiKey) {
        return popularTVShowsRepository.getPopularTVShows(page, apiKey);
    }
}
