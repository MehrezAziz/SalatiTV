package com.example.firstapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstapp.R
import com.example.firstapp.network.DateInfo
import com.example.firstapp.network.Timings
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun PrayerTimesScreen(
    timings: Timings,
    dateInfo: DateInfo
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Display the prayer times as cards
            PrayerTimeRow("الفجر", timings.Fajr)
            PrayerTimeRow("الظّهر", timings.Dhuhr)
            PrayerTimeRow("العصر", timings.Asr)
            PrayerTimeRow("المغرب", timings.Maghrib)
            PrayerTimeRow("العِشاء", timings.Isha)
        }
    }
}


@Composable
fun PrayerTimeRow(prayerName: String, time: String) {

            val adjustedTime = try {
            val timeParts = time.split(":").map { it.toInt() }
            val hours = timeParts[0]
            val minutes = timeParts[1] + 10

            if (minutes >= 60) {
                "%02d:%02d".format((hours + 1) % 24, minutes - 60)
            } else {
                "%02d:%02d".format(hours, minutes)
            }
        } catch (e: Exception) {
            "Invalid Time"
        }

        androidx.compose.material3.Card(
            modifier = Modifier
                .padding(8.dp),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFF2D2D2D)) // Dark background
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Prayer Name
                Text(
                    text = prayerName,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Time (big font)
                Text(
                    text = time,
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 36.sp),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Adjusted Time
                Text(
                    text = "${adjustedTime} الإقامة  ",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp, fontWeight = Bold),
                    color = Color.Gray
                )
            }
        }
    }

