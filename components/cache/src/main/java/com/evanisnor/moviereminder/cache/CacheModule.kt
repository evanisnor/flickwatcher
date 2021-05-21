package com.evanisnor.moviereminder.cache

import com.evanisnor.moviereminder.network.DaggerNetworkComponent
import com.evanisnor.moviereminder.network.NetworkComponent
import dagger.Module
import dagger.Provides

@Module
object CacheModule {

    @Provides
    fun networkComponent(): NetworkComponent = DaggerNetworkComponent.create()

    @Provides
    fun theMovieDbService(networkComponent: NetworkComponent) =
        networkComponent.getTheMovieDbController()
}