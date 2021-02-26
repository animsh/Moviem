package com.animsh.moviem

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.animsh.moviem.data.Repository
import com.animsh.moviem.models.movie.CommonMovieResponse
import com.animsh.moviem.util.Constants.Companion.hasInternetConnection
import com.animsh.moviem.util.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Created by animsh on 2/26/2021.
 */
class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private var movieResponse: MutableLiveData<NetworkResult<CommonMovieResponse>> =
        MutableLiveData()

    fun getTrendingMovies(apiKey: String, page: Int) = viewModelScope.launch {
        getTrendingMoviesSafeCall(apiKey, page)
    }

    private suspend fun getTrendingMoviesSafeCall(apiKey: String, page: Int) {
        movieResponse.value = NetworkResult.Loading()
        if (hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getTrendingMovies(apiKey, page)
                movieResponse.value = handleResponse(response)
            } catch (e: Exception) {
                movieResponse.value = NetworkResult.Error(message = "Movies Not Found!!")
            }
        } else {
            movieResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private fun handleResponse(response: Response<CommonMovieResponse>): NetworkResult<CommonMovieResponse>? {
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