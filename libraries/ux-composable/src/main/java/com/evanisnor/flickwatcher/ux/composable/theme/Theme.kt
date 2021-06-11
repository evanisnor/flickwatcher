package com.evanisnor.flickwatcher.ux.composable.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorPalette = darkColors(
    primary = Color(0xFFFFC850),
    secondary = Color(0xFFFFC850),
    background = Color(0xFF1B1B1E),
    surface = Color(0xFF4E4637),
    error = Color(0xFFD4AFB9),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black
)

@Composable
fun TrendingMoviesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}