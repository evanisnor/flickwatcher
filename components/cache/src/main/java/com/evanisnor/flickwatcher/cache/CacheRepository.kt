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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import javax.inject.Inject

@CacheScope
class CacheRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val cacheCoroutineScope: CoroutineScope,
    private val theMovieDbRepository: TheMovieDbRepository,
    private val dao: MovieDao,
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val ImageBaseUrlKey = stringPreferencesKey("ImageBaseUrl")
    }

    // region Image Base URL

    @ExperimentalCoroutinesApi
    suspend fun getImageBaseUrl() = channelFlow<String> {
        dataStore.data.map { preferences ->
            val storedImageBaseUrl = preferences[ImageBaseUrlKey]

            if (storedImageBaseUrl.isNullOrBlank()) {
                fetchImageBaseUrl().collect { updatedImageBaseUrl ->
                    dataStore.edit { settings ->
                        settings[ImageBaseUrlKey] = updatedImageBaseUrl
                        send(updatedImageBaseUrl)
                    }
                }
            } else {
                send(storedImageBaseUrl)
            }
        }.stateIn(cacheCoroutineScope)

        awaitClose { }
    }.flowOn(dispatcher)


    private suspend fun fetchImageBaseUrl() = flow {
        theMovieDbRepository.getImageBaseUrl()
            .catch { e ->
                Log.w(CacheRepository::class.simpleName, "Error fetching ImageBaseUrl: $e")
            }
            .collect { imageBaseUrl ->
                if (!imageBaseUrl.isNullOrBlank()) {
                    emit(imageBaseUrl)
                }
            }

    }.flowOn(dispatcher)

    // endregion

    // region Fetch Trending Movies

    @ExperimentalCoroutinesApi
    fun getTrendingMovies(localDate: LocalDate = LocalDate.now()) = flow<List<Movie>> {
        dao.getTrendingMovies().collect { cachedMovies ->

            // Always return any cached movies immediately
            if (cachedMovies.isNotEmpty()) {
                emit(cachedMovies)
            }

            // If there were no cached movies, or if the last known date is old, then attempt
            // to fetch latest from network.
            if (cachedMovies.isEmpty() ||
                cachedMovies.any { it.trendingDate?.isBefore(localDate) == true }) {

                fetchTrendingMovies()
            }
        }
    }.flowOn(dispatcher)

    private suspend fun fetchTrendingMovies() {
        theMovieDbRepository.getTrendingMovies()
            .catch { e ->
                Log.w(CacheRepository::class.simpleName, "Error fetching TrendingMovies: $e")
            }
            .collect { page ->
                // Convert the network-fetched Movie objects into local Movie objects and store them
                page?.results?.mapIndexed { i, movie ->
                    movie.toLocalTrending(i)
                }?.takeUnless {
                    it.isEmpty()
                }?.also { trendingMovies ->
                    // Return to the network to fetch image URLs for each movie
                    fetchMovieImageUrls(trendingMovies)
                }?.let { trendingMovies ->
                    dao.clearOldTrendingMovies()
                    dao.insertMovies(*trendingMovies.toTypedArray())
                }
            }
    }

    private suspend fun fetchMovieImageUrls(movies: List<Movie>) {
        val moviesMap = movies.associateBy { it.id }
        theMovieDbRepository.getTrendingMovieImageUrls(*moviesMap.keys.toIntArray())
            .catch { e ->
                Log.w(CacheRepository::class.simpleName, "Error fetching MovieImages: $e")
            }
            .collect { movieImagesList ->
                movieImagesList.forEach { movieImages ->
                    moviesMap[movieImages.id]?.apply {
                        posterUrl = movieImages.posters.firstOrNull()?.file_path
                        backdropUrl = movieImages.backdrops.firstOrNull()?.file_path
                    }
                }.also {
                    dao.updateMovies(*moviesMap.values.toTypedArray())
                }
            }
    }

    // endregion

}