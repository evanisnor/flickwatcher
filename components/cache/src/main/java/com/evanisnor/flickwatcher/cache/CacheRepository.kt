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
    fun getTrendingMovies(localDate: LocalDate = LocalDate.now()) = channelFlow<List<Movie>> {
        dao.getTrendingMovies(localDate).collect { cachedMovies ->
            if (cachedMovies.isEmpty()) {
                fetchTrendingMovies(localDate).collect { updatedMovies ->
                    send(updatedMovies)
                }
            } else {
                send(cachedMovies.sortedBy { it.trendingRank })
            }
        }

        awaitClose { }
    }.flowOn(dispatcher)

    private suspend fun fetchTrendingMovies(localDate: LocalDate) = flow {
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
                    dao.clearOldTrendingMovies(localDate)
                    emit(trendingMovies)
                }
            }
    }.flowOn(dispatcher)

    private suspend fun fetchMovieImages(movies: List<Movie>) {
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

    // endregion

}