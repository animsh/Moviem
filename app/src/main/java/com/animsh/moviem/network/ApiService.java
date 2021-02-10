package com.animsh.moviem.network;

import com.animsh.moviem.model.Movie;
import com.animsh.moviem.response.CommonMoviesResponse;
import com.animsh.moviem.response.UniqueMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by animsh on 2/10/2021.
 */
public interface ApiService {

    @GET("movie/popular")
    Call<CommonMoviesResponse> getPopularMovies(@Query("api_key") String apiKey
            , @Query("page") int page);

    @GET("movie/top_rated")
    Call<CommonMoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey
            , @Query("page") int page);

    @GET("movie/now_playing")
    Call<UniqueMovieResponse> getNowPlayingMovies(@Query("api_key") String apiKey
            , @Query("page") int page);

    @GET("movie/latest")
    Call<Movie> getLatestMovie(@Query("api_key") String apiKey);

    @GET("trending/movie/day")
    Call<CommonMoviesResponse> getTrendingMovies(@Query("api_key") String apiKey
            , @Query("page") int page);

}
