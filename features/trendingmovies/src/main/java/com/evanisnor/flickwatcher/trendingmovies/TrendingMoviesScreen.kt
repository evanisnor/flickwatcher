package com.evanisnor.flickwatcher.trendingmovies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.evanisnor.flickwatcher.cache.model.Movie
import com.evanisnor.flickwatcher.network.NetworkMonitor
import com.evanisnor.flickwatcher.ux.composable.*
import com.evanisnor.flickwatcher.ux.composable.theme.TrendingMoviesTheme
import com.google.accompanist.coil.rememberCoilPainter

// region Screen State

data class TrendingMoviesState(
    val movies: List<Movie>,
    val imageParameters: ImageParameters,
    val networkStatus: NetworkMonitor.Status
)

data class ImageParameters(
    val imageBaseUrl: String,
    val imageLoader: ImageLoader,
    val imageSize: String = "w780"
) {
    fun imageUrlOf(movie: Movie) = "${imageBaseUrl}/${imageSize}/${movie.posterUrl}"
}

// endregion

// region Screen

@Composable
fun TrendingMoviesScreen(
    modifier: Modifier = Modifier,
    state: TrendingMoviesState,
) {
    TrendingMoviesTheme {
        Surface {

            when {
                state.movies.isNotEmpty() -> {
                    MovieList(
                        modifier = modifier,
                        movies = state.movies,
                        imageParameters = state.imageParameters
                    )
                }
                state.networkStatus == NetworkMonitor.Status.Disconnected -> {
                    Disconnected()
                }
                else -> {
                    LoadingSpinner()
                }
            }

        }
    }
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    imageParameters: ImageParameters
) {
    val colors = MaterialTheme.colors

    BoxWithConstraints(
        contentAlignment = Alignment.TopEnd
    ) {
        RatchetScrollList(
            modifier = modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(
                    listOf(colors.surface, colors.background)
                )),
            items = movies
        ) { index, orientation, movie, ratchetScrollModifiers ->

            var cellWidth by remember { mutableStateOf(0.dp) }
            var cellHeight by remember { mutableStateOf(0.dp) }
            val padding = when {
                orientation.isPortrait() -> Modifier.padding(vertical = 32.dp, horizontal = 64.dp)
                orientation.isLandscape() -> Modifier.padding(vertical = 16.dp, horizontal = 42.dp)
                else -> Modifier
            }
            val spacerSize = Modifier.size(
                (maxWidth - cellWidth) / 2.3f,
                (maxHeight - cellHeight) / 2.3f
            )

            if (index == 0) {
                Spacer(modifier = Modifier.then(spacerSize))
            }

            MoviePoster(
                modifier = Modifier
                    .then(ratchetScrollModifiers)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(6.dp))
                    .then(padding)
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)

                        cellWidth = placeable.width.toDp()
                        cellHeight = placeable.height.toDp()

                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(0, 0)
                        }
                    },
                movie = movie,
                imageParameters = imageParameters
            )

            if (index == movies.size - 1) {
                Spacer(modifier = Modifier.then(spacerSize))
            }
        }
    }
}

// endregion

// region MoviePoster

@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    movie: Movie,
    imageParameters: ImageParameters
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.poster_placeholder),
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f),
            contentDescription = null,
            contentScale = ContentScale.Inside
        )

        Image(
            painter = rememberCoilPainter(
                request = imageParameters.imageUrlOf(movie),
                imageLoader = imageParameters.imageLoader,
                fadeIn = true
            ),
            modifier = Modifier.fillMaxSize(),
            contentDescription = null,
            contentScale = ContentScale.Inside
        )

        MovieRank(
            modifier = Modifier
                .size(95.dp)
                .offset(x = (-32).dp, y = (-32).dp),
            rank = movie.trendingRank
        )

    }
}

@Preview
@Composable
fun MovieRank(
    modifier: Modifier = Modifier,
    rank: Int = 1
) {
    val colors = MaterialTheme.colors
    val typography = MaterialTheme.typography
    val rankScale = if (rank == 1) 1f else 0.75f

    Box(modifier = modifier
        .scale(rankScale)) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = null
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "$rank",
            color = colors.onSecondary,
            style = typography.h4,
        )
    }
}

// endregion