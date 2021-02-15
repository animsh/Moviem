package com.animsh.moviem.repositories.moviesrepositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.animsh.moviem.network.ApiService;
import com.animsh.moviem.network.RetrofitClient;
import com.animsh.moviem.response.moviesresponse.CommonMoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by animsh on 2/15/2021.
 */
public class RecommendedMoviesRepository {
    private ApiService apiService;

    public RecommendedMoviesRepository() {
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
    }

    public MutableLiveData<CommonMoviesResponse> getRecommendedMovies(int movieId, int page, String apiKey) {
        MutableLiveData<CommonMoviesResponse> data = new MutableLiveData<>();
        apiService.getRecommendedMovies(movieId, apiKey, page).enqueue(new Callback<CommonMoviesResponse>() {
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
