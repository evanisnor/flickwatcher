package com.evanisnor.flickwatcher.trendingmovies

import com.evanisnor.libraries.viewmodelfactory.ViewModelFactoryModule
import com.evanisnor.flickwatcher.cache.CacheComponent
import com.evanisnor.flickwatcher.maincomponent.MainComponent
import dagger.BindsInstance
import dagger.Component

@TrendingMoviesScope
@Component(
    dependencies = [
        MainComponent::class,
        CacheComponent::class
    ],
    modules = [
        TrendingMoviesModule::class,
        ViewModelFactoryModule::class
    ]
)
interface TrendingMoviesComponent {

    @Component.Builder
    interface Builder {
        fun mainComponent(mainComponent: MainComponent): Builder
        fun cacheComponent(cacheComponent: CacheComponent): Builder

        @BindsInstance
        fun trendingMoviesActivity(trendingMoviesActivity: TrendingMoviesActivity): Builder

        fun build(): TrendingMoviesComponent
    }

    fun inject(trendingMoviesActivity: TrendingMoviesActivity)
}