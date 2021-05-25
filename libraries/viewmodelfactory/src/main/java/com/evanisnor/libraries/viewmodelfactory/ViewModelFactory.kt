package com.evanisnor.libraries.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evanisnor.flickwatcher.cache.CacheRepository
import com.evanisnor.flickwatcher.network.NetworkMonitor
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val cacheRepository: CacheRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(CacheRepository::class.java, NetworkMonitor::class.java)
            .newInstance(cacheRepository, networkMonitor)
}