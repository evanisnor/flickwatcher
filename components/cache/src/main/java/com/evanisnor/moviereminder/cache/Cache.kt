package com.evanisnor.moviereminder.cache

import android.util.Log
import com.evanisnor.moviereminder.model.Movie
import com.evanisnor.moviereminder.network.TheMovieDbController
import javax.inject.Inject

class Cache @Inject constructor(
    private val theMovieDbController: TheMovieDbController
) {

    fun loadTrendingMovies(onLoad: (List<Movie>) -> Unit) {
        theMovieDbController.getTrendingMovies({ results ->
            onLoad(results?.results ?: listOf())
        }, { throwable ->
            Log.e("Cache", "Error loading Trending Movies from Network: ${throwable.message}")
        })
    }

}