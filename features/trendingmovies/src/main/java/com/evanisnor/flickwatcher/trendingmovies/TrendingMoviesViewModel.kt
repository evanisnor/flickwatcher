package com.evanisnor.flickwatcher.trendingmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.evanisnor.flickwatcher.cache.CacheRepository
import com.evanisnor.flickwatcher.cache.model.Movie
import kotlinx.coroutines.runBlocking

@TrendingMoviesScope
class TrendingMoviesViewModel constructor(
    private val cacheRepository: CacheRepository
) : ViewModel() {

    val trendingMovies: LiveData<List<Movie>>
        get() = cacheRepository.receiveTrendingMovies().asLiveData()

    val imageBaseUrl: String
        get() = cacheRepository.receiveImageBaseUrl() ?: ""

    fun update() = runBlocking {
        cacheRepository.fetchImageBaseUrl()
        cacheRepository.fetchTrendingMovies()
    }
}