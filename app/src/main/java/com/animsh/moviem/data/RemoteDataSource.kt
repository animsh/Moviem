package com.animsh.moviem.data

import com.animsh.moviem.data.network.TMDBApi
import com.animsh.moviem.models.movie.CommonMovieResponse
import com.animsh.moviem.models.movie.Movie
import com.animsh.moviem.models.movie.UniqueMovieResponse
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by animsh on 2/26/2021.
 */
class RemoteDataSource @Inject constructor(
    private val tmdbApi: TMDBApi
) {

    suspend fun getLatestMovie(apiKey: String): Response<Movie> {
        return tmdbApi.getLatestMovie(apiKey)
    }

    suspend fun getTrendingMovies(apiKey: String, page: Int): Response<CommonMovieResponse> {
        return tmdbApi.getTrendingMovie(apiKey, page)
    }

    suspend fun getPopularMovies(apiKey: String, page: Int): Response<CommonMovieResponse> {
        return tmdbApi.getPopularMovies(apiKey, page)
    }

    suspend fun getTopMovies(apiKey: String, page: Int): Response<CommonMovieResponse> {
        return tmdbApi.getTopRatedMovies(apiKey, page)
    }

    suspend fun getNowPlayingMovies(apiKey: String, page: Int): Response<UniqueMovieResponse> {
        return tmdbApi.getNowPlayingMovies(apiKey, page)
    }
}