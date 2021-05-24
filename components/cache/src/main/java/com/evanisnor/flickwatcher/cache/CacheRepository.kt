package com.evanisnor.flickwatcher.cache

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.evanisnor.flickwatcher.cache.database.MovieDao
import com.evanisnor.flickwatcher.cache.model.Movie
import com.evanisnor.flickwatcher.cache.model.toLocalTrending
import com.evanisnor.flickwatcher.network.TheMovieDbRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@CacheScope
class CacheRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val theMovieDbRepository: TheMovieDbRepository,
    private val dao: MovieDao,
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val ImageBaseUrlKey = stringPreferencesKey("ImageBaseUrl")
    }

    // region Image Base URL

    fun receiveImageBaseUrl() = dataStore.data.map { preferences ->
        preferences[ImageBaseUrlKey] ?: ""
    }

    suspend fun fetchImageBaseUrl() = coroutineScope {
        launch(dispatcher) {
            theMovieDbRepository.getImageBaseUrl()
                .catch { e ->
                    Log.w(CacheRepository::class.simpleName, "Error fetching ImageBaseUrl: $e")
                }
                .collect { imageBaseUrl ->
                    if (!imageBaseUrl.isNullOrBlank()) {
                        dataStore.edit { settings ->
                            settings[ImageBaseUrlKey] = imageBaseUrl
                        }
                    }
                }
        }
    }

    // endregion

    // region Fetch Trending Movies

    fun receiveTrendingMovies() = flow {
        // Hard coding for today
        val today = LocalDate.now()

        dao.getTrendingMovies(today).collect { movies ->
            emit(movies.sortedBy { it.trendingRank })
        }
    }

    suspend fun fetchTrendingMovies() = coroutineScope {
        launch(dispatcher) {
            // Hard coding for today
            val today = LocalDate.now()

            theMovieDbRepository.getTrendingMovies()
                .catch { e ->
                    Log.w(CacheRepository::class.simpleName, "Error fetching TrendingMovies: $e")
                }
                .collect { page ->
                    // Convert the network-fetched Movie objects into local Movie objects and store them
                    page?.results?.mapIndexed { i, movie ->
                        movie.toLocalTrending(i)
                    }?.also { trendingMovies ->
                        // Return to the network to fetch image URLs for each movie
                        fetchMovieImages(trendingMovies)
                    }?.let { trendingMovies ->
                        dao.insertMovies(*trendingMovies.toTypedArray())
                        dao.clearOldTrendingMovies(today)
                    }

                }
        }
    }

    private suspend fun fetchMovieImages(movies: List<Movie>) = coroutineScope {
        launch(dispatcher) {
            val moviesMap = movies.associateBy { it.id }
            theMovieDbRepository.getTrendingMovieImageUrls(*moviesMap.keys.toIntArray())
                .catch { e ->
                    Log.w(CacheRepository::class.simpleName, "Error fetching MovieImages: $e")
                }
                .collect { movieImages ->
                    movieImages?.apply {
                        moviesMap[id]?.let { movie ->
                            movie.posterUrl = posters.firstOrNull()?.file_path
                            movie.backdropUrl = backdrops.firstOrNull()?.file_path
                            dao.updateMovie(movie)
                        }
                    }
                }
        }

    }

    // endregion

}