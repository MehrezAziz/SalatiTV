package com.example.firstapp.network

data class DateInfo(
    val readable: String,
    val hijri: HijriDate,
    val gregorian: GregorianDate
)
