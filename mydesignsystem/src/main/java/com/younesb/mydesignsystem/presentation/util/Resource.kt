package com.younesb.mydesignsystem.presentation.util

import com.younesb.mydesignsystem.domain.Error

typealias DomainError = Error

sealed interface Resource<out D, out E: DomainError> {
    data object Idle: Resource<Nothing, Nothing>
    data object Loading: Resource<Nothing, Nothing>
    data class Success<out D>(val data: D): Resource<D, Nothing>
    data class Error<out E : DomainError>(val error: E): Resource<Nothing, E>
}

fun <T, R, E: DomainError> Resource<T, E>.map(transform: (T) -> R) =
    when (this) {
        is Resource.Idle -> Resource.Idle
        is Resource.Loading -> Resource.Loading
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(error)
    }

fun <T, E: DomainError> Resource<T, E>.getOrNull(): T? =
    when (this) {
        is Resource.Idle -> null
        is Resource.Loading -> null
        is Resource.Success -> data
        is Resource.Error -> null
    }

