package com.evanisnor.libraries.viewmodelfactory

import com.evanisnor.moviereminder.cache.CacheRepository
import dagger.Module
import dagger.Provides

@Module
object ViewModelFactoryModule {

    @Provides
    fun viewModelFactory(cacheRepository: CacheRepository) = ViewModelFactory(cacheRepository)
}