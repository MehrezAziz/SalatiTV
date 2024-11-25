package com.example.firstapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.firstapp.ui.components.MessageCard
import com.example.firstapp.ui.models.Message
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Preview
@Composable
fun PreviewMessageCard() {
    MessageCard(
        Message(
            author = "Al Nour",
            body = "بسم الله الرحمان الرحيم",
            miladi = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
        )
    )
}
