package com.evanisnor.libraries.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evanisnor.moviereminder.cache.CacheRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val cacheRepository: CacheRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(CacheRepository::class.java).newInstance(cacheRepository)
}