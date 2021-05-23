package com.evanisnor.moviereminder

import android.content.Context
import com.evanisnor.moviereminder.cache.CacheComponent
import com.evanisnor.moviereminder.trendingmovies.TrendingMoviesActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        CacheComponent::class
    ],
    modules = [
        MainModule::class
    ]
)
interface MainComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun cacheComponent(cacheComponent: CacheComponent): Builder

        fun build(): MainComponent
    }


    fun inject(trendingMoviesActivity: TrendingMoviesActivity)
}