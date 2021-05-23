package com.evanisnor.moviereminder.trendingmovies

import com.evanisnor.moviereminder.cache.CacheComponent
import com.evanisnor.moviereminder.maincomponent.MainComponent
import dagger.Component

@TrendingMoviesScope
@Component(
    dependencies = [
        MainComponent::class,
        CacheComponent::class
    ]
)
interface TrendingMoviesComponent {

    @Component.Builder
    interface Builder {
        fun mainComponent(mainComponent: MainComponent): Builder
        fun cacheComponent(cacheComponent: CacheComponent): Builder
        fun build(): TrendingMoviesComponent
    }

    fun inject(trendingMoviesActivity: TrendingMoviesActivity)
}