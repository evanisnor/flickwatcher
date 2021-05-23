package com.evanisnor.moviereminder.trendingmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evanisnor.moviereminder.cache.model.Movie
import com.evanisnor.moviereminder.maincomponent.MainApplication
import com.evanisnor.moviereminder.trendingmovies.ui.theme.MoviereminderTheme
import javax.inject.Inject

@TrendingMoviesScope
class TrendingMoviesActivity : ComponentActivity() {

    @Composable
    fun TrendingMoviesScreen(trendingMoviesViewModel: TrendingMoviesViewModel) {
        val movies by trendingMoviesViewModel.trendingMovies.observeAsState(emptyList())
        MoviereminderTheme {
            Surface {
                TrendingMovies(movies = movies)
            }
        }
    }

    @Composable
    fun TrendingMovies(movies: List<Movie>) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            items(movies) { movie ->
                TrendingMovie(movie = movie)
            }
        }
    }

    @Composable
    fun TrendingMovie(movie: Movie) {
        val typography = MaterialTheme.typography
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${movie.trendingRank}", style = typography.h1)
            Text(
                text = movie.title,
                style = typography.h6,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp),
                textAlign = TextAlign.Left,
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

    @Inject
    lateinit var viewModel: TrendingMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application = application as MainApplication
        DaggerTrendingMoviesComponent.builder()
            .mainComponent(application.mainComponent)
            .cacheComponent(application.mainComponent.cacheComponent())
            .trendingMoviesActivity(this)
            .build()
            .inject(this)

        viewModel.update()

        setContent {
            TrendingMoviesScreen(trendingMoviesViewModel = viewModel)
        }
    }

    // endregion

}
