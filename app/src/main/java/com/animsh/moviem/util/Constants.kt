package com.animsh.moviem.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Created by animsh on 2/25/2021.
 */
class Constants {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3"
        const val API_KEY = "Your Api Key"
        const val IMAGE_ORIGINAL = "https://image.tmdb.org/t/p/original"
        const val IMAGE_W500 = "https://image.tmdb.org/t/p/w500"

        fun hasInternetConnection(application: Application): Boolean {
            val connectivityManager = application.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    }
}