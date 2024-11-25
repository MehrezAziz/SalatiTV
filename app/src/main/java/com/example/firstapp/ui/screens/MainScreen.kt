package com.example.firstapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstapp.R
import com.example.firstapp.network.DateInfo
import com.example.firstapp.network.Timings
import java.time.LocalTime
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun MainScreen(
    timings: Timings? = null,
    dateInfo: DateInfo? = null,
    location: String
) {
    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    var remainingTime by remember { mutableStateOf("") }

    // Continuously update the current time and remaining time every second
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // 1 second delay
            currentTime = getCurrentTime()
            timings?.let {
                val nextPrayerTime = getNextPrayerTime(it)
                remainingTime = calculateRemainingTime(nextPrayerTime)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize() // Full screen container
    ) {
        // Background Image
        Image(
            painter = painterResource(R.drawable.makkah), // Replace with your image resource
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop, // Ensures the image fills the box while maintaining its aspect ratio
            modifier = Modifier.fillMaxSize() // Ensures the image fills the entire Box
        )

        // Foreground Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp) // Adds padding to the content
        ) {
            // MessageCard with sample content
            MessageCard(
                title = "Hijri: ${dateInfo?.hijri?.date} (${dateInfo?.hijri?.month?.en} - ${dateInfo?.hijri?.year})",
                subtitle = "بسم الله الرّحمَان الرّحيم   ${location} ",
                description = dateInfo?.readable ?: "Fetching date..."
            )

            // Spacer for separation
            Spacer(modifier = Modifier.height(16.dp))

            // Current Time Display
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Card(
                    modifier = Modifier
                        .padding(5.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = androidx.compose.material3.CardDefaults.cardElevation(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color(0xFF2D2D2D)) // Dark background
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Current Time
                        Text(
                            text = currentTime,
                            color = Color.White,
                            style = MaterialTheme.typography.displayLarge.copy(fontSize = 52.sp, fontWeight = FontWeight.Bold), // Large font size
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Remaining Time to Next Prayer
                        Text(
                            text = "${remainingTime} الوقت المتبقي للصلاة  ",
                            color = Color.Green,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 32.sp), // Slightly smaller font for remaining time
                        )
                    }
                }
            }

            // Prayer Times Content
            if (timings != null && dateInfo != null) {
                PrayerTimesScreen(timings = timings, dateInfo = dateInfo)
            } else {
                Text(
                    text = "Loading prayer times...",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center) // Centers the loading text
                )
            }
        }
    }
}

// Utility function to get the current time in hh:mm:ss format
fun getCurrentTime(): String {
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return timeFormat.format(Date())
}

// Function to get the next prayer time from the timings
@SuppressLint("NewApi")
fun getNextPrayerTime(timings: Timings?): LocalTime {
    val currentTime = LocalTime.now()

    // Parse the prayer times from the Timings object
    val fajrTime = LocalTime.parse(timings?.Fajr ?: "00:00")
    val dhuhrTime = LocalTime.parse(timings?.Dhuhr ?: "00:00")
    val asrTime = LocalTime.parse(timings?.Asr ?: "00:00")
    val maghribTime = LocalTime.parse(timings?.Maghrib ?: "00:00")
    val ishaTime = LocalTime.parse(timings?.Isha ?: "00:00")

    // List of prayer times in order for the day
    val prayerTimes = listOf(fajrTime, dhuhrTime, asrTime, maghribTime, ishaTime)

    // Find the next prayer time, which could be the first prayer of the next cycle
    for (prayerTime in prayerTimes) {
        if (prayerTime.isAfter(currentTime)) {
            return prayerTime
        }
    }

    // If all prayers have passed for today, return the first prayer (Fajr) of the next cycle
    return prayerTimes.first()
}

@SuppressLint("NewApi")
fun calculateRemainingTime(nextPrayerTime: LocalTime): String {
    val currentTime = LocalTime.now()

    // Create a LocalDateTime for "24:00:00" (end of the current day)
    val midnight = LocalDateTime.of(LocalDate.now(), LocalTime.MAX) // 23:59:59.999999999
    val midnight00 = LocalTime.MIDNIGHT
    val timeUntilMidnight = Duration.between(currentTime, midnight.toLocalTime())

    // Calculate the duration between the current time and the next prayer time
    var duration = Duration.between(currentTime, nextPrayerTime)

    if (duration.isNegative) {
        duration = Duration.between(midnight00, nextPrayerTime)

        val totalRemainingTime = timeUntilMidnight.toMinutes() + duration.toMinutes()

        var hours = totalRemainingTime / 60
        var minutes = totalRemainingTime % 60
        return "${hours}h:${minutes}m"
    } else {
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        return "${hours}h:${minutes}m"
    }
}
