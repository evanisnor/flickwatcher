package com.evanisnor.flickwatcher.trendingmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.evanisnor.flickwatcher.cache.CacheRepository
import com.evanisnor.flickwatcher.cache.model.Movie
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@TrendingMoviesScope
class TrendingMoviesViewModel constructor(
    private val cacheRepository: CacheRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            cacheRepository.fetchImageBaseUrl()
            cacheRepository.fetchTrendingMovies()
        }
    }

    val trendingMovies: LiveData<List<Movie>>
        get() = cacheRepository.receiveTrendingMovies().asLiveData()

    val imageBaseUrl: String
        get() = runBlocking { cacheRepository.receiveImageBaseUrl().first() }
}