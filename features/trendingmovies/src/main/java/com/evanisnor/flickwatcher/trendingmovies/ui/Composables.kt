package com.evanisnor.flickwatcher.trendingmovies.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun LoadingSpinner() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun BackdropOverlay() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RectangleShape)
            .alpha(0.5f)
            .background(Color.Black)
    )
}

@Composable
fun EmptyAndDisconnected() {
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sorry!",
            style = typography.h3
        )
        Text(
            text = "Connect to WiFi or mobile network to see today's trending movies",
            style = typography.body1
        )
    }
}
