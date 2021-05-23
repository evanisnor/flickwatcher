package com.evanisnor.moviereminder.cache

import com.evanisnor.moviereminder.cache.database.MovieDao
import com.evanisnor.moviereminder.cache.model.Movie
import com.evanisnor.moviereminder.network.TheMovieDbRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import com.evanisnor.moviereminder.network.model.Movie as NetworkMovie

@CacheScope
class CacheRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val theMovieDbRepository: TheMovieDbRepository,
    private val dao: MovieDao
) {

    fun receiveTrendingMovies() = flow {
        dao.getTrendingMovies().collect { movies ->
            emit(movies.sortedBy { it.trendingRank })
        }
    }

    fun fetchTrendingMovies() = runBlocking {
        launch(dispatcher) {
            dao.deleteTrendingMovies()
            theMovieDbRepository.getTrendingMovies().collect { page ->
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
        trendingRank = index + 1 // Offset by 1 so rank starts at 1 instead of 0
    )

}