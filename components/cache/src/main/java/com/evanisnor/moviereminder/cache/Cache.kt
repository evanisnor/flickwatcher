package com.evanisnor.moviereminder.cache

import com.evanisnor.moviereminder.cache.database.MovieDao
import com.evanisnor.moviereminder.cache.model.Movie
import com.evanisnor.moviereminder.network.TheMovieDbController
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import com.evanisnor.moviereminder.network.model.Movie as NetworkMovie

class Cache @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val theMovieDbController: TheMovieDbController,
    private val dao: MovieDao
) {

    fun receiveTrendingMovies(): Flow<List<Movie>> = dao.getTrendingMovies()

    fun fetchTrendingMovies() = runBlocking {
        launch(dispatcher) {
            dao.deleteTrendingMovies()
            theMovieDbController.getTrendingMovies().collect { page ->
                page?.results?.mapIndexed { i, movie ->
                    convertTrending(movie, i)
                }?.let { trendingMovies ->
                    dao.insertMovies(*trendingMovies.toTypedArray())
                }
            }
        }
    }

    private fun convertTrending(networkMovie: NetworkMovie, index: Int) = Movie(
        networkMovie.id,
        networkMovie.title,
        networkMovie.overview,
        networkMovie.release_date,
        networkMovie.adult,
        networkMovie.video,
        networkMovie.popularity,
        trending = true,
        trendingRank = index
    )

}