package com.evanisnor.flickwatcher.trendingmovies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import com.evanisnor.flickwatcher.maincomponent.FlickwatcherActivity
import com.evanisnor.flickwatcher.maincomponent.MainApplication
import com.evanisnor.flickwatcher.ux.composable.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@TrendingMoviesScope
class TrendingMoviesActivity : FlickwatcherActivity() {

    // region Lifecycle

    @Inject
    lateinit var viewModel: TrendingMoviesViewModel

    @Inject
    lateinit var imageLoader: ImageLoader

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

                viewModel.networkStatus.collect { networkStatus ->
                    viewModel.trendingMovies.collect { movies ->
                        val state = TrendingMoviesState(
                            movies = movies,
                            imageParameters = ImageParameters(
                                imageBaseUrl = viewModel.imageBaseUrl.value,
                                imageLoader = imageLoader
                            ),
                            networkStatus = networkStatus
                        )

                        withContext(Dispatchers.Main) {
                            setContent {
                                TrendingMoviesScreen(state = state)
                            }
                        }
                    }
                }

            }
        }
    }

    // endregion
}
