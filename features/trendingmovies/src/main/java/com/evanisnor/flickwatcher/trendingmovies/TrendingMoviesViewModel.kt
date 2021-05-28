package com.evanisnor.flickwatcher.trendingmovies

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evanisnor.flickwatcher.cache.CacheRepository
import com.evanisnor.flickwatcher.cache.model.Movie
import com.evanisnor.flickwatcher.network.NetworkMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Keep
class TrendingMoviesViewModel constructor(
    private val cacheRepository: CacheRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _networkStatus = MutableStateFlow(NetworkMonitor.Status.Unknown)
    val networkStatus: StateFlow<NetworkMonitor.Status> = _networkStatus

    private val _trendingMovies = MutableStateFlow(emptyList<Movie>())
    val trendingMovies: StateFlow<List<Movie>> = _trendingMovies

    private val _imageBaseUrl: MutableStateFlow<String> = MutableStateFlow("")
    val imageBaseUrl: StateFlow<String> = _imageBaseUrl

    init {
        watchNetworkState()
        fetchImageBaseUrl()
        fetchTrendingMovies()
    }

    private fun fetchTrendingMovies() = viewModelScope.launch {
        cacheRepository.getTrendingMovies().collect { movies ->
            _trendingMovies.value = movies
        }
    }

    private fun fetchImageBaseUrl() = viewModelScope.launch {
        cacheRepository.getImageBaseUrl().collect { url ->
            _imageBaseUrl.value = url
        }
    }

    private fun watchNetworkState() = viewModelScope.launch {
        networkMonitor.networkState().collect { status ->
            if (_networkStatus.value != NetworkMonitor.Status.Connected && status == NetworkMonitor.Status.Connected) {
                // back online?
                fetchTrendingMovies()
            }
            _networkStatus.value = status
        }
    }
}