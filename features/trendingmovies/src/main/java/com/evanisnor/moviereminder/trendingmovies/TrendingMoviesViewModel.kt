package com.evanisnor.moviereminder.trendingmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.evanisnor.moviereminder.cache.Cache
import com.evanisnor.moviereminder.cache.model.Movie

@TrendingMoviesScope
class TrendingMoviesViewModel constructor(
    private val cache: Cache
) : ViewModel() {

    val trendingMovies: LiveData<List<Movie>>
        get() = cache.receiveTrendingMovies().asLiveData()

    fun update() = cache.fetchTrendingMovies()
}