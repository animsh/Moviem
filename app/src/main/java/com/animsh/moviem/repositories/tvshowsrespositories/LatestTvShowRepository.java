package com.animsh.moviem.repositories.tvshowsrespositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.animsh.moviem.model.TVShow;
import com.animsh.moviem.network.ApiService;
import com.animsh.moviem.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by animsh on 2/12/2021.
 */
public class LatestTvShowRepository {
    private ApiService apiService;

    public LatestTvShowRepository() {
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShow> getLatestTvShow(String apiKey) {
        MutableLiveData<TVShow> data = new MutableLiveData<>();
        apiService.getLatestTVShow(apiKey).enqueue(new Callback<TVShow>() {
            @Override
            public void onResponse(@NonNull Call<TVShow> call, @NonNull Response<TVShow> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShow> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
