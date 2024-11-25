package com.example.firstapp.network

data class GregorianDate(
    val date: String,
    val format: String,
    val day: String,
    val weekday: Weekday,
    val month: MonthInfo,
    val year: String,
    val designation: Designation
)