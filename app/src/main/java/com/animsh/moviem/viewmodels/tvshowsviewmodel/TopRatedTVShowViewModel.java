package com.animsh.moviem.viewmodels.tvshowsviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.animsh.moviem.repositories.tvshowsrespositories.TopRatedTVShowsRepository;
import com.animsh.moviem.response.tvshowzresponse.TvShowResponse;

/**
 * Created by animsh on 2/12/2021.
 */
public class TopRatedTVShowViewModel extends ViewModel {

    private TopRatedTVShowsRepository topRatedTVShowsRepository;

    public TopRatedTVShowViewModel() {
        topRatedTVShowsRepository = new TopRatedTVShowsRepository();
    }

    public LiveData<TvShowResponse> getTopRatedTVShows(int page, String apiKey) {
        return topRatedTVShowsRepository.getTopRatedTVShow(page, apiKey);
    }

}
