package com.younesb.mydesignsystem.presentation.util

fun Long.toStringWithSuffix(suffix: String): String {
    return "$this$suffix"
}

fun Long.toMinutesSecondsString(): String {
    val minutes = this / 60
    val seconds = this % 60
    return String.format("%02d:%02d", minutes, seconds)
}

fun Long.toHoursMinutesString(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    return String.format("%02d:%02d", hours, minutes)
}

fun Long.toHoursMinutesSecondsString(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

fun Long.toReadableFileSizeString(): String {
    val kiloByte = 1024L
    val megaByte = kiloByte * 1024
    val gigaByte = megaByte * 1024

    return when {
        this >= gigaByte -> String.format("%.2f GB", this.toDouble() / gigaByte)
        this >= megaByte -> String.format("%.2f MB", this.toDouble() / megaByte)
        this >= kiloByte -> String.format("%.2f KB", this.toDouble() / kiloByte)
        else -> "$this B"
    }
}

fun Long.toPercentageString(): String {
    return "$this%"
}
