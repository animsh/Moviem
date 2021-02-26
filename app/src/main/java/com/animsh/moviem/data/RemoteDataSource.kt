package com.animsh.moviem.data

import com.animsh.moviem.data.network.TMDBApi
import com.animsh.moviem.models.movie.CommonMovieResponse
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by animsh on 2/26/2021.
 */
class RemoteDataSource @Inject constructor(
    private val tmdbApi: TMDBApi
) {
    suspend fun getTrendingMovies(apiKey: String, page: Int): Response<CommonMovieResponse> {
        return tmdbApi.getTrendingTv(apiKey, page)
    }
}