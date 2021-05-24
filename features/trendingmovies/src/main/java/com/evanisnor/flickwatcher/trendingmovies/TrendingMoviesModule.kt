package com.evanisnor.flickwatcher.trendingmovies

import androidx.lifecycle.ViewModelProvider
import com.evanisnor.flickwatcher.cache.CacheRepository
import com.evanisnor.libraries.viewmodelfactory.ViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.runBlocking
import javax.inject.Named


@Module
object TrendingMoviesModule {

    @Provides
    @TrendingMoviesScope
    fun viewModel(
        trendingMoviesActivity: TrendingMoviesActivity,
        viewModelFactory: ViewModelFactory
    ): TrendingMoviesViewModel =
        ViewModelProvider(trendingMoviesActivity, viewModelFactory).get(
            TrendingMoviesViewModel::class.java
        )
}