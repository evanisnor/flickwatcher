package com.evanisnor.moviereminder.network

import dagger.Component

@Component(
    modules = [
        NetworkModule::class
    ]
)
interface NetworkComponent {

    fun getTheMovieDbController(): TheMovieDbController

}