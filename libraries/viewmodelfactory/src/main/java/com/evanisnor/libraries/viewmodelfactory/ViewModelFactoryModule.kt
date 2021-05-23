package com.evanisnor.libraries.viewmodelfactory

import com.evanisnor.moviereminder.cache.Cache
import dagger.Module
import dagger.Provides

@Module
object ViewModelFactoryModule {

    @Provides
    fun vmFactory(cache: Cache) = ViewModelFactory(cache)
}