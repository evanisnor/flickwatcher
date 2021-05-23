package com.evanisnor.moviereminder

import com.evanisnor.moviereminder.trendingmovies.TrendingMoviesComponent
import dagger.Module

@Module(
    subcomponents = [
        TrendingMoviesComponent::class
    ]
)
object MainModule {
}