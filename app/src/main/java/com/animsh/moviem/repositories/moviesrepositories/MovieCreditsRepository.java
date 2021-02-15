package com.animsh.moviem.repositories.moviesrepositories;

import androidx.lifecycle.MutableLiveData;

import com.animsh.moviem.network.ApiService;
import com.animsh.moviem.network.RetrofitClient;
import com.animsh.moviem.response.CreditsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by animsh on 2/15/2021.
 */
public class MovieCreditsRepository {
    private ApiService apiService;

    public MovieCreditsRepository() {
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
    }

    public MutableLiveData<CreditsResponse> getMovieCredits(int movieId, String apiKey) {
        MutableLiveData<CreditsResponse> data = new MutableLiveData<>();
        apiService.getMovieCredits(movieId, apiKey).enqueue(new Callback<CreditsResponse>() {
            @Override
            public void onResponse(Call<CreditsResponse> call, Response<CreditsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CreditsResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
