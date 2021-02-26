package com.animsh.moviem.util

/**
 * Created by animsh on 2/26/2021.
 */
sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(data: T? = null, message: String?) : NetworkResult<T>(data, message)
    class Loading<T> : NetworkResult<T>()
}
