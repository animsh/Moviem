package com.animsh.moviem.repositories.tvshowsrespositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.animsh.moviem.network.ApiService;
import com.animsh.moviem.network.RetrofitClient;
import com.animsh.moviem.response.tvshowzresponse.TvShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by animsh on 2/12/2021.
 */
public class TopRatedTVShowsRepository {
    private ApiService apiService;

    public TopRatedTVShowsRepository() {
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TvShowResponse> getTopRatedTVShow(int page, String apiKey) {
        MutableLiveData<TvShowResponse> data = new MutableLiveData<>();
        apiService.getTopRatedTVShow(apiKey, page).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
