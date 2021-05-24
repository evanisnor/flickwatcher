package com.evanisnor.flickwatcher.trendingmovies

import android.content.Context
import com.evanisnor.flickwatcher.cache.CacheComponent
import com.evanisnor.flickwatcher.maincomponent.MainComponent
import com.evanisnor.flickwatcher.network.NetworkComponent
import com.evanisnor.libraries.viewmodelfactory.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component

@TrendingMoviesScope
@Component(
    dependencies = [
        MainComponent::class,
        CacheComponent::class,
        NetworkComponent::class
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
        fun networkComponent(networkComponent: NetworkComponent): Builder
        fun cacheComponent(cacheComponent: CacheComponent): Builder

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun trendingMoviesActivity(trendingMoviesActivity: TrendingMoviesActivity): Builder

        fun build(): TrendingMoviesComponent
    }

    fun inject(trendingMoviesActivity: TrendingMoviesActivity)
}