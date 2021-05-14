package com.animsh.moviem.data.network

import com.animsh.moviem.models.movie.CommonMovieResponse
import com.animsh.moviem.models.movie.CreditsResponse
import com.animsh.moviem.models.movie.Movie
import com.animsh.moviem.models.movie.UniqueMovieResponse
import com.animsh.moviem.models.tv.TV
import com.animsh.moviem.models.tv.TvResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by animsh on 2/25/2021.
 */
interface TMDBApi {

    @GET("3/movie/latest")
    suspend fun getLatestMovie(
        @Query("api_key") apiKey: String
    ): Response<Movie>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<Movie>

    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<CommonMovieResponse>

    @GET("3/movie/{movie_id}/recommendations")
    suspend fun getRecommendationsMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<CommonMovieResponse>

    @GET("3/movie/{movie_id}/credits")
    suspend fun getCreditsMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<CreditsResponse>

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


    @GET("3/tv/latest")
    suspend fun getLatestTVShow(@Query("api_key") apiKey: String): Response<TV>

    @GET("3/tv/{tv_id}")
    suspend fun getTVShowDetails(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String
    ): Response<TV>

    @GET("3/tv/airing_today")
    suspend fun getTVAiringToday(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<TvResponse>

    @GET("3/tv/on_the_air")
    suspend fun getTVOnAirToday(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<TvResponse>

    @GET("3/tv/popular")
    suspend fun getPopularTVShow(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<TvResponse>

    @GET("3/tv/top_rated")
    suspend fun getTopRatedTVShow(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<TvResponse>

    @GET("3/trending/tv/day")
    suspend fun getTrendingTVShows(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<TvResponse>

}