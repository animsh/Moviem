package com.animsh.moviem.repositories.moviesrepositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.animsh.moviem.model.Movie;
import com.animsh.moviem.network.ApiService;
import com.animsh.moviem.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by animsh on 2/10/2021.
 */
public class LatestMovieRepository {

    private final ApiService apiService;

    public LatestMovieRepository() {
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<Movie> getLatestMovie(String apiKey) {
        MutableLiveData<Movie> data = new MutableLiveData<>();
        apiService.getLatestMovie(apiKey).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
