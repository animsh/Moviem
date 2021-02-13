package com.animsh.moviem.repositories.tvshowsrespositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.animsh.moviem.model.TVShow;
import com.animsh.moviem.network.ApiService;
import com.animsh.moviem.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by animsh on 2/13/2021.
 */
public class TVShowDetailsRepository {
    private ApiService apiService;

    public TVShowDetailsRepository() {
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
    }

    public MutableLiveData<TVShow> getTVShowDetails(int tvId, String apiKey) {
        MutableLiveData<TVShow> data = new MutableLiveData<>();
        apiService.getTVShowDetails(tvId, apiKey).enqueue(new Callback<TVShow>() {
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
