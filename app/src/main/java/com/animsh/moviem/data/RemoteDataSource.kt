package com.animsh.moviem.data

import com.animsh.moviem.data.network.TMDBApi
import com.animsh.moviem.models.movie.*
import com.animsh.moviem.models.tv.TV
import com.animsh.moviem.models.tv.TvResponse
import com.animsh.moviem.models.tv.episodes.SeasonResponse
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

    suspend fun getSimilarMoviesDetails(
        movieId: Int,
        apiKey: String,
        page: Int
    ): Response<CommonMovieResponse> {
        return tmdbApi.getSimilarMovie(movieId, apiKey, page)
    }

    suspend fun getRecommendationMoviesDetails(
        movieId: Int,
        apiKey: String,
        page: Int
    ): Response<CommonMovieResponse> {
        return tmdbApi.getRecommendationsMovie(movieId, apiKey, page)
    }

    suspend fun getMovieCreditsDetails(
        movieId: Int,
        apiKey: String
    ): Response<CreditsResponse> {
        return tmdbApi.getCreditsMovie(movieId, apiKey)
    }

    suspend fun getUpcomingMovies(apiKey: String, page: Int): Response<ComingSoonResponse> {
        return tmdbApi.getUpcomingMovie(apiKey, page)
    }

    suspend fun searchMovies(apiKey: String, query: String): Response<CommonMovieResponse> {
        return tmdbApi.searchMovie(apiKey, query)
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

    suspend fun searchTVShow(apiKey: String, query: String): Response<TvResponse> {
        return tmdbApi.searchTVShow(apiKey, query)
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

    suspend fun getSimilarTVDetails(
        tvId: Int,
        apiKey: String,
        page: Int
    ): Response<TvResponse> {
        return tmdbApi.getSimilarTV(tvId, apiKey, page)
    }

    suspend fun getRecommendationTVDetails(
        tvId: Int,
        apiKey: String,
        page: Int
    ): Response<TvResponse> {
        return tmdbApi.getRecommendationsTV(tvId, apiKey, page)
    }

    suspend fun getTVCreditsDetails(
        tvId: Int,
        apiKey: String
    ): Response<CreditsResponse> {
        return tmdbApi.getCreditsTV(tvId, apiKey)
    }

    suspend fun getTVEpisodes(
        tvId: Int,
        seasonNumber: Int,
        apiKey: String
    ): Response<SeasonResponse> {
        return tmdbApi.getTVEpisodes(tvId, seasonNumber, apiKey)
    }
}