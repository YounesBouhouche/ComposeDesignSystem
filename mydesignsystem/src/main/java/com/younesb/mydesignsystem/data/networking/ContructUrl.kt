package com.younesb.mydesignsystem.data.networking

fun constructUrl(
    baseUrl: String,
    endpoint: String = "",
): String {
    return if (baseUrl.endsWith("/")) {
        if (endpoint.startsWith("/")) {
            baseUrl + endpoint.removePrefix("/")
        } else {
            baseUrl + endpoint
        }
    } else {
        if (endpoint.startsWith("/")) {
            baseUrl + endpoint
        } else {
            "$baseUrl/$endpoint"
        }
    }
}