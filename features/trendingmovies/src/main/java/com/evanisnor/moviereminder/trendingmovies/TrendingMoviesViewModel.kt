package com.evanisnor.moviereminder.trendingmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.evanisnor.moviereminder.cache.CacheRepository
import com.evanisnor.moviereminder.cache.model.Movie

@TrendingMoviesScope
class TrendingMoviesViewModel constructor(
    private val cacheRepository: CacheRepository
) : ViewModel() {

    val trendingMovies: LiveData<List<Movie>>
        get() = cacheRepository.receiveTrendingMovies().asLiveData()

    fun update() = cacheRepository.fetchTrendingMovies()
}