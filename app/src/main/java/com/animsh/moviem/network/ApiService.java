package com.animsh.moviem.network;

import com.animsh.moviem.model.Movie;
import com.animsh.moviem.model.TVShow;
import com.animsh.moviem.response.moviesresponse.CommonMoviesResponse;
import com.animsh.moviem.response.moviesresponse.UniqueMovieResponse;
import com.animsh.moviem.response.tvshowzresponse.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    @GET("tv/airing_today")
    Call<TvShowResponse> getTVAiringToday(@Query("api_key") String apiKey
            , @Query("page") int page);

    @GET("tv/on_the_air")
    Call<TvShowResponse> getTVOnAirToday(@Query("api_key") String apiKey
            , @Query("page") int page);

    @GET("tv/popular")
    Call<TvShowResponse> getPopularTVShow(@Query("api_key") String apiKey
            , @Query("page") int page);

    @GET("tv/top_rated")
    Call<TvShowResponse> getTopRatedTVShow(@Query("api_key") String apiKey
            , @Query("page") int page);

    @GET("tv/latest")
    Call<TVShow> getLatestTVShow(@Query("api_key") String apiKey);

    @GET("trending/tv/day")
    Call<TvShowResponse> getTrendingTVShows(@Query("api_key") String apiKey
            , @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") int movie_id
            , @Query("api_key") String apiKey);

    @GET("tv/{tv_id}")
    Call<TVShow> getTVShowDetails(@Path("tv_id") int tv_id
            , @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Call<CommonMoviesResponse> getSimilarMovies(@Path("movie_id") int movie_id
            , @Query("api_key") String apiKey
            , @Query("page") int page);
}
