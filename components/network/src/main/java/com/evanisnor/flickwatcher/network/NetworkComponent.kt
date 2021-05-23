package com.evanisnor.flickwatcher.network

import dagger.Component

@NetworkScope
@Component(
    modules = [
        NetworkModule::class
    ]
)
interface NetworkComponent {

    fun getTheMovieDbRepository(): TheMovieDbRepository

}