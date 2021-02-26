package com.animsh.moviem.data.network

import com.animsh.moviem.models.movie.CommonMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by animsh on 2/25/2021.
 */
interface TMDBApi {

    @GET("/trending/movie/day")
    suspend fun getTrendingTv(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): Response<CommonMovieResponse>
}