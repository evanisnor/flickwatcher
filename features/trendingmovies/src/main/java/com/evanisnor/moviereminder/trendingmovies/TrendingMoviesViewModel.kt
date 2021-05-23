package com.evanisnor.moviereminder.trendingmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.evanisnor.moviereminder.cache.Cache
import com.evanisnor.moviereminder.cache.model.Movie

class TrendingMoviesViewModel : ViewModel() {

    lateinit var cache: Cache

    val trendingMovies: LiveData<List<Movie>>
        get() = cache.receiveTrendingMovies().asLiveData()

    fun fetch() = cache.fetchTrendingMovies()
}