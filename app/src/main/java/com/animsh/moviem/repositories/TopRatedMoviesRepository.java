package com.animsh.moviem.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.animsh.moviem.network.ApiService;
import com.animsh.moviem.network.RetrofitClient;
import com.animsh.moviem.response.CommonMoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by animsh on 2/10/2021.
 */
public class TopRatedMoviesRepository {

    private final ApiService apiService;

    public TopRatedMoviesRepository() {
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<CommonMoviesResponse> getTopRatedMovies(int page, String apiKey) {
        MutableLiveData<CommonMoviesResponse> data = new MutableLiveData<>();
        apiService.getTopRatedMovies(apiKey, page).enqueue(new Callback<CommonMoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonMoviesResponse> call, @NonNull Response<CommonMoviesResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CommonMoviesResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
