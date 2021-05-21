package com.evanisnor.moviereminder.network

import dagger.Component

@NetworkScope
@Component(
    modules = [
        NetworkModule::class
    ]
)
interface NetworkComponent {

    fun getTheMovieDbController(): TheMovieDbController

}