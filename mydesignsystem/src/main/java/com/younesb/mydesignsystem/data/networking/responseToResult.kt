package com.younesb.mydesignsystem.data.networking

import com.younesb.mydesignsystem.domain.NetworkError
import com.younesb.mydesignsystem.domain.Result


inline fun <reified T> responseToResult(
    body: T,
    code: Int,
): Result<T, NetworkError> {
    return when (code) {
        in 200..299 -> {
            try {
                Result.Success(body)
            } catch (_: Exception) {
                Result.Error(NetworkError.SERIALIZATION_ERROR)
            }
        }
        400 -> Result.Error(NetworkError.BAD_REQUEST)
        401 -> Result.Error(NetworkError.UNAUTHORIZED)
        403 -> Result.Error(NetworkError.FORBIDDEN)
        404 -> Result.Error(NetworkError.NOT_FOUND)
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}
