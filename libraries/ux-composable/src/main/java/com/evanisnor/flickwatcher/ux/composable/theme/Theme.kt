package com.evanisnor.flickwatcher.ux.composable.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.evanisnor.flickwatcher.ux.composable.theme.Typography

private val ColorPalette = darkColors(
    primary = Color(0xFF66C3FF),
    secondary = Color(0xFF53D8FB),
    background = Color(0xFF121212),
    surface = Color.Black,
    error = Color(0xFFD4AFB9),
    onPrimary = Color.White,
    onSecondary = Color.White,
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