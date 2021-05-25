package com.evanisnor.flickwatcher.network

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient

@NetworkScope
@Component(
    modules = [
        NetworkModule::class
    ]
)
interface NetworkComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): NetworkComponent
    }

    // Exposing OkHttpClient instance so it can be used by Coil ImageLoader
    // https://github.com/coil-kt/coil/issues/46
    fun okHttpClient(): OkHttpClient

    fun getNetworkMonitor(): NetworkMonitor

    fun getTheMovieDbRepository(): TheMovieDbRepository

}