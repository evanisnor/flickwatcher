package com.evanisnor.flickwatcher.network

import com.evanisnor.flickwatcher.network.model.MovieImages
import com.evanisnor.flickwatcher.network.model.Page
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@NetworkScope
class TheMovieDbRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val theMovieDbService: TheMovieDbService
) {

    suspend fun getImageBaseUrl(): Flow<String?> = flow {
        val response = theMovieDbService.getConfiguration()
        emit(response.body()?.images?.secure_base_url)
    }.flowOn(dispatcher)

    suspend fun getTrendingMovies(): Flow<Page?> = flow {
        val response = theMovieDbService.getTrendingMoviesToday()
        emit(response.body())
    }.flowOn(dispatcher)

    suspend fun getTrendingMovieImageUrls(vararg moviesIds: Int): Flow<List<MovieImages>> = flow {
        val movieImages: MutableList<MovieImages> = mutableListOf()
        moviesIds.forEach { id ->
            theMovieDbService.getMovieImages(id).body()?.let {
                movieImages.add(it)
            }
        }.also {
            emit(movieImages)
        }
    }.flowOn(dispatcher)
}
