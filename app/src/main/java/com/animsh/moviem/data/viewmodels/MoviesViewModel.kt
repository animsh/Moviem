package com.animsh.moviem.data.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.animsh.moviem.data.repositories.Repository
import com.animsh.moviem.models.movie.CommonMovieResponse
import com.animsh.moviem.models.movie.Movie
import com.animsh.moviem.models.movie.UniqueMovieResponse
import com.animsh.moviem.util.Constants.Companion.hasInternetConnection
import com.animsh.moviem.util.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Created by animsh on 2/26/2021.
 */
class MoviesViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var latestMovieResponse: MutableLiveData<NetworkResult<Movie>> = MutableLiveData()

    var trendingMovieResponse: MutableLiveData<NetworkResult<CommonMovieResponse>> =
        MutableLiveData()

    var popularMovieResponse: MutableLiveData<NetworkResult<CommonMovieResponse>> =
        MutableLiveData()

    var topMoviesResponse: MutableLiveData<NetworkResult<CommonMovieResponse>> =
        MutableLiveData()

    var nowPlayingMoviesResponse: MutableLiveData<NetworkResult<UniqueMovieResponse>> =
        MutableLiveData()

    fun getLatestMovie(apiKey: String) = viewModelScope.launch {
        getLatestMovieSafeCall(apiKey)
    }

    fun getTrendingMovies(apiKey: String, page: Int) = viewModelScope.launch {
        getTrendingMoviesSafeCall(apiKey, page)
    }

    fun getPopularMovies(apiKey: String, page: Int) = viewModelScope.launch {
        getPopularMoviesSafeCall(apiKey, page)
    }

    fun getTopMovies(apiKey: String, page: Int) = viewModelScope.launch {
        getTopMoviesSafeCall(apiKey, page)
    }

    fun getNowPlayingMovies(apiKey: String, page: Int) = viewModelScope.launch {
        getNowPlayingMoviesSafeCall(apiKey, page)
    }

    private suspend fun getLatestMovieSafeCall(apiKey: String) {
        latestMovieResponse.value = NetworkResult.Loading()
        if (hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getLatestMovie(apiKey)
                latestMovieResponse.value = handleResponse(response)
            } catch (e: Exception) {
                latestMovieResponse.value = NetworkResult.Error(message = "Movie Not Found!!")
            }
        } else {
            latestMovieResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private fun handleResponse(response: Response<Movie>): NetworkResult<Movie>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(message = "Timeout!!!")
            }
            response.code() == 402 -> {
                return NetworkResult.Error(message = "Quota Exceeded!!")
            }
            response.body() == null -> {
                return NetworkResult.Error(message = "Movie not found.")
            }
            response.isSuccessful -> {
                val movies = response.body()
                return NetworkResult.Success(movies!!)
            }
            else -> {
                return NetworkResult.Error(message = response.message())
            }
        }
    }

    private suspend fun getNowPlayingMoviesSafeCall(apiKey: String, page: Int) {
        nowPlayingMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getNowPlayingMovies(apiKey, page)
                nowPlayingMoviesResponse.value = handleUniqueResponse(response)
            } catch (e: Exception) {
                nowPlayingMoviesResponse.value = NetworkResult.Error(message = "Movies Not Found!!")
            }
        } else {
            nowPlayingMoviesResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private suspend fun getTopMoviesSafeCall(apiKey: String, page: Int) {
        topMoviesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getTopMovies(apiKey, page)
                topMoviesResponse.value = handleCommonResponse(response)
            } catch (e: Exception) {
                topMoviesResponse.value = NetworkResult.Error(message = "Movies Not Found!!")
            }
        } else {
            topMoviesResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private suspend fun getPopularMoviesSafeCall(apiKey: String, page: Int) {
        popularMovieResponse.value = NetworkResult.Loading()
        if (hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getPopularMovies(apiKey, page)
                popularMovieResponse.value = handleCommonResponse(response)
            } catch (e: Exception) {
                popularMovieResponse.value = NetworkResult.Error(message = "Movies Not Found!!")
            }
        } else {
            popularMovieResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private suspend fun getTrendingMoviesSafeCall(apiKey: String, page: Int) {
        trendingMovieResponse.value = NetworkResult.Loading()
        if (hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getTrendingMovies(apiKey, page)
                trendingMovieResponse.value = handleCommonResponse(response)
            } catch (e: Exception) {
                trendingMovieResponse.value = NetworkResult.Error(message = "Movies Not Found!!")
            }
        } else {
            trendingMovieResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private fun handleCommonResponse(response: Response<CommonMovieResponse>): NetworkResult<CommonMovieResponse>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(message = "Timeout!!!")
            }
            response.code() == 402 -> {
                return NetworkResult.Error(message = "Quota Exceeded!!")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error(message = "Movies not found.")
            }
            response.isSuccessful -> {
                val movies = response.body()
                return NetworkResult.Success(movies!!)
            }
            else -> {
                return NetworkResult.Error(message = response.message())
            }
        }
    }

    private fun handleUniqueResponse(response: Response<UniqueMovieResponse>): NetworkResult<UniqueMovieResponse>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(message = "Timeout!!!")
            }
            response.code() == 402 -> {
                return NetworkResult.Error(message = "Quota Exceeded!!")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error(message = "Movies not found.")
            }
            response.isSuccessful -> {
                val movies = response.body()
                return NetworkResult.Success(movies!!)
            }
            else -> {
                return NetworkResult.Error(message = response.message())
            }
        }
    }

}