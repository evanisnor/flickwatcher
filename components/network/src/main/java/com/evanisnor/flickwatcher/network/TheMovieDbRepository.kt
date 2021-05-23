package com.evanisnor.flickwatcher.network

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

    suspend fun getTrendingMovies(): Flow<Page?> = flow {
        val response = theMovieDbService.getTrendingMoviesToday()
        emit(response.body())
    }.flowOn(dispatcher)

}
