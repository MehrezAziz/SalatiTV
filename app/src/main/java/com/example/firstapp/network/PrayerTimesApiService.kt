package com.example.firstapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface PrayerTimesApiService {
    @GET("v1/timingsByAddress")
    suspend fun getPrayerTimes(
        @Query("date") date: String,
        @Query("address") address: String,
        @Query("method") method: Int
    ): PrayerTimesResponse
}
