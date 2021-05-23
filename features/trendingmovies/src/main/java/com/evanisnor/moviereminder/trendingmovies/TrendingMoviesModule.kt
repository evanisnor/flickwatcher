package com.evanisnor.moviereminder.trendingmovies

import androidx.lifecycle.ViewModelProvider
import com.evanisnor.libraries.viewmodelfactory.ViewModelFactory
import dagger.Module
import dagger.Provides


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