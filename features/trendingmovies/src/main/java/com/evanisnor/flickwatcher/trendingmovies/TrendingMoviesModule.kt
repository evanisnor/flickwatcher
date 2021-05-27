package com.evanisnor.flickwatcher.trendingmovies

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import com.evanisnor.libraries.viewmodelfactory.ViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient


@Module
object TrendingMoviesModule {

    @ExperimentalCoroutinesApi
    @Provides
    @TrendingMoviesScope
    fun viewModel(
        trendingMoviesActivity: TrendingMoviesActivity,
        viewModelFactory: ViewModelFactory
    ): TrendingMoviesViewModel =
        ViewModelProvider(trendingMoviesActivity, viewModelFactory).get(
            TrendingMoviesViewModel::class.java
        )

    @Provides
    @TrendingMoviesScope
    fun imageLoader(context: Context, okHttpClient: OkHttpClient) = ImageLoader.Builder(context)
        .okHttpClient(okHttpClient)
        .build()
}