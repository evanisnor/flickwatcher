package com.evanisnor.flickwatcher.network

import dagger.Component
import okhttp3.OkHttpClient

@NetworkScope
@Component(
    modules = [
        NetworkModule::class
    ]
)
interface NetworkComponent {

    // Exposing OkHttpClient instance so it can be used by Coil ImageLoader
    // https://github.com/coil-kt/coil/issues/46
    fun okHttpClient(): OkHttpClient

    fun getTheMovieDbRepository(): TheMovieDbRepository

}