package com.younesb.mydesignsystem.domain

enum class NetworkError: Error {
    NO_INTERNET,
    SERIALIZATION_ERROR,
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    SERVER_ERROR,
    UNKNOWN
}