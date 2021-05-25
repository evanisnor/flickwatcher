package com.evanisnor.flickwatcher.trendingmovies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import com.evanisnor.flickwatcher.cache.model.Movie
import com.evanisnor.flickwatcher.maincomponent.FlickwatcherActivity
import com.evanisnor.flickwatcher.maincomponent.MainApplication
import com.evanisnor.flickwatcher.network.NetworkMonitor
import com.evanisnor.flickwatcher.trendingmovies.ui.BackdropOverlay
import com.evanisnor.flickwatcher.trendingmovies.ui.EmptyAndDisconnected
import com.evanisnor.flickwatcher.trendingmovies.ui.LoadingSpinner
import com.evanisnor.flickwatcher.trendingmovies.ui.theme.TrendingMoviesTheme
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@TrendingMoviesScope
class TrendingMoviesActivity : FlickwatcherActivity() {

    @ExperimentalCoroutinesApi
    @Composable
    fun TrendingMoviesScreen(
        movies: List<Movie>,
        imageBaseUrl: String = viewModel.imageBaseUrl.value,
        networkStatus: NetworkMonitor.Status = viewModel.networkStatus.value
    ) {
        TrendingMoviesTheme {
            Surface {
                if (movies.isNotEmpty()) {
                    TrendingMovies(
                        movies = movies,
                        imageBaseUrl = imageBaseUrl
                    )
                } else if (movies.isEmpty() && (networkStatus == NetworkMonitor.Status.Disconnected || networkStatus == NetworkMonitor.Status.Unknown)) {
                    EmptyAndDisconnected()
                } else {
                    LoadingSpinner()
                }
            }
        }
    }

    @Composable
    fun TrendingMovies(
        movies: List<Movie>,
        imageBaseUrl: String = ""
    ) {
        LazyColumn {
            items(movies) { movie ->
                TrendingMovie(
                    movie = movie,
                    imageBaseUrl = imageBaseUrl
                )
            }
        }
    }

    @Composable
    fun TrendingMovie(
        movie: Movie,
        imageBaseUrl: String
    ) {
        Box(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {

            MovieBackdrop(
                movie = movie,
                imageBaseUrl = imageBaseUrl
            )
            BackdropOverlay()
            MovieLabel(movie = movie)

        }
    }

    @Composable
    fun MovieLabel(movie: Movie) {
        val typography = MaterialTheme.typography

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = "${movie.trendingRank}",
                color = Color.White,
                style = typography.h1
            )
            Text(
                text = movie.title,
                color = Color.White,
                style = typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                textAlign = TextAlign.Left
            )
        }
    }

    @Composable
    fun MovieBackdrop(
        movie: Movie,
        imageBaseUrl: String
    ) {
        movie.posterUrl?.let {
            Image(
                painter = rememberCoilPainter(
                    "${imageBaseUrl}/w780/${movie.backdropUrl}",
                    imageLoader = imageLoader
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
        }
    }

    // region Preview

    @Preview
    @Composable
    fun PreviewGreeting() {
        TrendingMovies(
            movies = listOf(
                Movie(title = "Borat", trendingRank = 1),
                Movie(title = "2001: A Space Odyssey", trendingRank = 2)
            )
        )
    }

    // endregion

    // region Lifecycle

    @ExperimentalCoroutinesApi
    @Inject
    lateinit var viewModel: TrendingMoviesViewModel

    @Inject
    lateinit var imageLoader: ImageLoader

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            launch(Dispatchers.IO) {
                val application = application as MainApplication
                DaggerTrendingMoviesComponent.builder()
                    .mainComponent(application.mainComponent)
                    .networkComponent(application.mainComponent.networkComponent())
                    .cacheComponent(application.mainComponent.cacheComponent())
                    .context(this@TrendingMoviesActivity)
                    .trendingMoviesActivity(this@TrendingMoviesActivity)
                    .build()
                    .inject(this@TrendingMoviesActivity)

                load()
            }
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun load() {
        viewModel.trendingMovies.combine(viewModel.networkStatus) { movies, status ->
            Pair<List<Movie>, NetworkMonitor.Status>(movies, status)
        }.collect { pair ->
            withContext(Dispatchers.Main) {
                setContent {
                    TrendingMoviesScreen(
                        movies = pair.first,
                        networkStatus = pair.second
                    )
                }
            }
        }
    }

}

// endregion

