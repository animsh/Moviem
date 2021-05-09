package com.animsh.moviem.data

import com.animsh.moviem.data.network.TMDBApi
import com.animsh.moviem.models.movie.CommonMovieResponse
import com.animsh.moviem.models.movie.Movie
import com.animsh.moviem.models.movie.UniqueMovieResponse
import com.animsh.moviem.models.tv.TV
import com.animsh.moviem.models.tv.TvResponse
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

    suspend fun getMovieDetails(movieId: Int, apiKey: String): Response<Movie> {
        return tmdbApi.getMovieDetails(movieId, apiKey)
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

    suspend fun getLatestTV(apiKey: String): Response<TV> {
        return tmdbApi.getLatestTVShow(apiKey)
    }

    suspend fun getTVDetails(tvId: Int, apiKey: String): Response<TV> {
        return tmdbApi.getTVShowDetails(tvId, apiKey)
    }

    suspend fun getTvAiringToday(apiKey: String, page: Int): Response<TvResponse> {
        return tmdbApi.getTVAiringToday(apiKey, page)
    }

    suspend fun getOnAirTv(apiKey: String, page: Int): Response<TvResponse> {
        return tmdbApi.getTVOnAirToday(apiKey, page)
    }

    suspend fun getPopularTv(apiKey: String, page: Int): Response<TvResponse> {
        return tmdbApi.getPopularTVShow(apiKey, page)
    }

    suspend fun getTopTv(apiKey: String, page: Int): Response<TvResponse> {
        return tmdbApi.getTopRatedTVShow(apiKey, page)
    }

    suspend fun getTrendingTv(apiKey: String, page: Int): Response<TvResponse> {
        return tmdbApi.getTrendingTVShows(apiKey, page)
    }
}