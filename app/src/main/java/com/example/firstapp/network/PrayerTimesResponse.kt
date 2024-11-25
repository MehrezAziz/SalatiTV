package com.example.firstapp.network

data class PrayerTimesResponse(
    val code: Int,
    val status: String,
    val data: PrayerData
)