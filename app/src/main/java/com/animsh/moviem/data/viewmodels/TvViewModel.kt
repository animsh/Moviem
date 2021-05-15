package com.animsh.moviem.data.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.animsh.moviem.data.repositories.Repository
import com.animsh.moviem.models.movie.CreditsResponse
import com.animsh.moviem.models.tv.TV
import com.animsh.moviem.models.tv.TvResponse
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Created by animsh on 2/28/2021.
 */
class TvViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var latestTvResponse: MutableLiveData<NetworkResult<TV>> = MutableLiveData()

    var tvDetailsResponse: MutableLiveData<NetworkResult<TV>> = MutableLiveData()

    var similarTvResponse: MutableLiveData<NetworkResult<TvResponse>> = MutableLiveData()

    var recommendedTvResponse: MutableLiveData<NetworkResult<TvResponse>> = MutableLiveData()

    var creditResponse: MutableLiveData<NetworkResult<CreditsResponse>> = MutableLiveData()

    var tvOnAirResponse: MutableLiveData<NetworkResult<TvResponse>> = MutableLiveData()

    var tvAiringTodayResponse: MutableLiveData<NetworkResult<TvResponse>> = MutableLiveData()

    var popularTvOResponse: MutableLiveData<NetworkResult<TvResponse>> = MutableLiveData()

    var topTvResponse: MutableLiveData<NetworkResult<TvResponse>> = MutableLiveData()

    var trendingResponse: MutableLiveData<NetworkResult<TvResponse>> = MutableLiveData()

    fun getLatestTv(apiKey: String) = viewModelScope.launch {
        getLatestTvSafeCAll(apiKey)
    }

    fun getTvDetails(tvId: Int, apiKey: String) = viewModelScope.launch {
        getTvDetailsSafeCAll(tvId, apiKey)
    }

    fun getSimilarTv(tvId: Int, apiKey: String, page: Int) = viewModelScope.launch {
        getSimilarTvSafeCall(tvId, apiKey, page)
    }

    fun getRecommendedTv(tvId: Int, apiKey: String, page: Int) = viewModelScope.launch {
        getRecommendedTvSafeCall(tvId, apiKey, page)
    }

    fun getCreditsTv(tvId: Int, apiKey: String) = viewModelScope.launch {
        getCreditSafeCall(tvId, apiKey)
    }

    fun getOnAirTv(apiKey: String, page: Int) = viewModelScope.launch {
        getOnAirTvSafeCall(apiKey, page)
    }

    fun getAiringToday(apiKey: String, page: Int) = viewModelScope.launch {
        getAiringTodaySafeCall(apiKey, page)
    }

    private suspend fun getAiringTodaySafeCall(apiKey: String, page: Int) {
        tvAiringTodayResponse.value = NetworkResult.Loading()
        if (Constants.hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getTvAiringToday(apiKey, page)
                tvAiringTodayResponse.value = handleListResponse(response)
            } catch (e: Exception) {
                tvAiringTodayResponse.value = NetworkResult.Error(message = "Tv Not Found!!")
            }
        } else {
            tvAiringTodayResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    fun getPopularTv(apiKey: String, page: Int) = viewModelScope.launch {
        getPopularTvSafeCall(apiKey, page)
    }

    private suspend fun getPopularTvSafeCall(apiKey: String, page: Int) {
        popularTvOResponse.value = NetworkResult.Loading()
        if (Constants.hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getPopularTv(apiKey, page)
                popularTvOResponse.value = handleListResponse(response)
            } catch (e: Exception) {
                popularTvOResponse.value = NetworkResult.Error(message = "o Tv Not Found!!")
            }
        } else {
            popularTvOResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    fun getTopTv(apiKey: String, page: Int) = viewModelScope.launch {
        getTopTvSafeCall(apiKey, page)
    }

    private suspend fun getTopTvSafeCall(apiKey: String, page: Int) {
        topTvResponse.value = NetworkResult.Loading()
        if (Constants.hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getTopTv(apiKey, page)
                topTvResponse.value = handleListResponse(response)
            } catch (e: Exception) {
                topTvResponse.value = NetworkResult.Error(message = "0 Tv Not Found!!")
            }
        } else {
            topTvResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    fun getTrendingTv(apiKey: String, page: Int) = viewModelScope.launch {
        getTrendingTvSafeCall(apiKey, page)
    }

    private suspend fun getTrendingTvSafeCall(apiKey: String, page: Int) {
        trendingResponse.value = NetworkResult.Loading()
        if (Constants.hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getTrendingTv(apiKey, page)
                trendingResponse.value = handleListResponse(response)
            } catch (e: Exception) {
                trendingResponse.value =
                    NetworkResult.Error(message = e.message + "0 TV Not Found!!")
            }
        } else {
            trendingResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private suspend fun getOnAirTvSafeCall(apiKey: String, page: Int) {
        tvOnAirResponse.value = NetworkResult.Loading()
        if (Constants.hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getOnAirTv(apiKey, page)
                tvOnAirResponse.value = handleListResponse(response)
            } catch (e: Exception) {
                tvOnAirResponse.value = NetworkResult.Error(message = "TV Not Found!!")
            }
        } else {
            tvAiringTodayResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private suspend fun getSimilarTvSafeCall(tvId: Int, apiKey: String, page: Int) {
        similarTvResponse.value = NetworkResult.Loading()
        if (Constants.hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getSimilarTVDetails(tvId, apiKey, page)
                similarTvResponse.value = handleListResponse(response)
            } catch (e: Exception) {
                similarTvResponse.value = NetworkResult.Error(message = "Tv Not Found!!")
            }
        } else {
            similarTvResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private suspend fun getRecommendedTvSafeCall(tvId: Int, apiKey: String, page: Int) {
        recommendedTvResponse.value = NetworkResult.Loading()
        if (Constants.hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getRecommendationTVDetails(tvId, apiKey, page)
                recommendedTvResponse.value = handleListResponse(response)
            } catch (e: Exception) {
                recommendedTvResponse.value = NetworkResult.Error(message = "Tv Not Found!!")
            }
        } else {
            recommendedTvResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private suspend fun getCreditSafeCall(tvId: Int, apiKey: String) {
        creditResponse.value = NetworkResult.Loading()
        if (Constants.hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getTVCreditsDetails(tvId, apiKey)
                creditResponse.value = handleCreditResponse(response)
            } catch (e: Exception) {
                creditResponse.value = NetworkResult.Error(message = "Tv Not Found!!")
            }
        } else {
            creditResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private fun handleCreditResponse(response: Response<CreditsResponse>): NetworkResult<CreditsResponse>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(message = "Timeout!!!")
            }
            response.code() == 402 -> {
                return NetworkResult.Error(message = "Quota Exceeded!!")
            }
            response.body() == null -> {
                return NetworkResult.Error(message = "Credits not found.")
            }
            response.isSuccessful -> {
                val credits = response.body()
                return NetworkResult.Success(credits!!)
            }
            else -> {
                return NetworkResult.Error(message = response.message())
            }
        }
    }

    private fun handleListResponse(response: Response<TvResponse>): NetworkResult<TvResponse> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(message = "Timeout!!!")
            }
            response.code() == 402 -> {
                return NetworkResult.Error(message = "Quota Exceeded!!")
            }
            response.body() == null -> {
                return NetworkResult.Error(message = "Tv not found.")
            }
            response.isSuccessful -> {
                val tv = response.body()
                return NetworkResult.Success(tv!!)
            }
            else -> {
                return NetworkResult.Error(message = response.message())
            }
        }
    }

    private suspend fun getLatestTvSafeCAll(apiKey: String) {
        latestTvResponse.value = NetworkResult.Loading()
        if (Constants.hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getLatestTV(apiKey)
                latestTvResponse.value = handleResponse(response)
            } catch (e: Exception) {
                latestTvResponse.value = NetworkResult.Error(message = "Tv Not Found!!")
            }
        } else {
            latestTvResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private suspend fun getTvDetailsSafeCAll(tvId: Int, apiKey: String) {
        tvDetailsResponse.value = NetworkResult.Loading()
        if (Constants.hasInternetConnection(getApplication())) {
            try {
                val response = repository.remote.getTVDetails(tvId, apiKey)
                tvDetailsResponse.value = handleResponse(response)
            } catch (e: Exception) {
                tvDetailsResponse.value =
                    NetworkResult.Error(message = "Tv Not Found!!" + e.message)
            }
        } else {
            tvDetailsResponse.value = NetworkResult.Error(message = "No Internet Connection")
        }
    }

    private fun handleResponse(response: Response<TV>): NetworkResult<TV> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(message = "Timeout!!!")
            }
            response.code() == 402 -> {
                return NetworkResult.Error(message = "Quota Exceeded!!")
            }
            response.body() == null -> {
                return NetworkResult.Error(message = "Tv not found.")
            }
            response.isSuccessful -> {
                val tv = response.body()
                return NetworkResult.Success(tv!!)
            }
            else -> {
                return NetworkResult.Error(message = response.message())
            }
        }
    }

}