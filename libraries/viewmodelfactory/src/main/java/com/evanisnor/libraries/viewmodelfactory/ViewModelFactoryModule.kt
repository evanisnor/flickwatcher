package com.evanisnor.libraries.viewmodelfactory

import com.evanisnor.flickwatcher.cache.CacheRepository
import com.evanisnor.flickwatcher.network.NetworkMonitor
import dagger.Module
import dagger.Provides

@Module
object ViewModelFactoryModule {

    @Provides
    fun viewModelFactory(
        cacheRepository: CacheRepository,
        networkMonitor: NetworkMonitor
    ) = ViewModelFactory(cacheRepository, networkMonitor)
}