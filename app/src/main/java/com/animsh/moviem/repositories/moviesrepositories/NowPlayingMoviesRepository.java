package com.animsh.moviem.repositories.moviesrepositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.animsh.moviem.network.ApiService;
import com.animsh.moviem.network.RetrofitClient;
import com.animsh.moviem.response.moviesresponse.UniqueMovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by animsh on 2/10/2021.
 */
public class NowPlayingMoviesRepository {

    private final ApiService apiService;

    public NowPlayingMoviesRepository() {
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<UniqueMovieResponse> getNowPlayingMovies(int page, String apiKey) {
        MutableLiveData<UniqueMovieResponse> data = new MutableLiveData<>();
        apiService.getNowPlayingMovies(apiKey, page).enqueue(new Callback<UniqueMovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<UniqueMovieResponse> call, @NonNull Response<UniqueMovieResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UniqueMovieResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
