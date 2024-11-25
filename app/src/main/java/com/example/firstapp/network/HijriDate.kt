package com.example.firstapp.network

data class HijriDate(
    val date: String,
    val format: String,
    val day: String,
    val weekday: Weekday,
    val month: MonthInfo,
    val year: String,
    val designation: Designation
)