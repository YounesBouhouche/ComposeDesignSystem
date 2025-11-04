package com.younesb.mydesignsystem.data.networking

import com.younesb.mydesignsystem.domain.NetworkError
import com.younesb.mydesignsystem.domain.Result
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException


suspend inline fun <reified T, HR> safeCall(
    getBody: HR.() -> T,
    getCode: HR.() -> Int,
    call: () -> HR
): Result<T, NetworkError> {
    val response = try {
        call()
    } catch (_: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (_: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION_ERROR)
    } catch (_: Exception) {
        currentCoroutineContext().ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }
    return responseToResult(response.getBody(), response.getCode())
}