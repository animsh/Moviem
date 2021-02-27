package com.animsh.moviem.data.network

import com.animsh.moviem.models.movie.CommonMovieResponse
import com.animsh.moviem.models.movie.Movie
import com.animsh.moviem.models.movie.UniqueMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by animsh on 2/25/2021.
 */
interface TMDBApi {

    @GET("3/movie/latest\n")
    suspend fun getLatestMovie(
        @Query("api_key") apiKey: String
    ): Response<Movie>

    @GET("3/trending/movie/day")
    suspend fun getTrendingMovie(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<CommonMovieResponse>

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<CommonMovieResponse>

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<CommonMovieResponse>

    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<UniqueMovieResponse>


}