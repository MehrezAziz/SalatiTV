package com.example.firstapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstapp.R

@Composable
fun MessageCard(title: String, subtitle: String, description: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Example Background Image (Replace with Coil/ImageLoader for network images)


        // Text content
        Row (
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0f),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(0.dp)

            ) {
                Text(
                    text = title,
                    color= Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier=Modifier
                        .offset(y=5.dp,x=5.dp)

                )
            }

            Box(
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0f),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(0.dp)
            ) {
                Text(
                    text = subtitle,
                    color= Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier=Modifier
                        .offset(y=5.dp,x=5.dp)
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0f),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(0.dp)
            ) {
                Text(
                    text = description,
                    color= Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    modifier=Modifier
                        .offset(y=5.dp,x=5.dp)
                )
            }
        }
    }
}
