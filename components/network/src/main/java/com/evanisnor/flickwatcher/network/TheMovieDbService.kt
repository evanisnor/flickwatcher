package com.evanisnor.flickwatcher.network

import com.evanisnor.flickwatcher.network.model.Configuration
import com.evanisnor.flickwatcher.network.model.MovieImages
import com.evanisnor.flickwatcher.network.model.Page
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit interface for The Movie Database API
 *
 * https://developers.themoviedb.org/3/getting-started/introduction
 */
interface TheMovieDbService {

    companion object {
        const val baseUrl = "https://api.themoviedb.org/"
    }

    @GET("/3/configuration")
    suspend fun getConfiguration(): Response<Configuration>

    @GET("/3/trending/movie/day")
    suspend fun getTrendingMoviesToday(): Response<Page>

    @GET("/3/movie/{id}/images")
    suspend fun getMovieImages(@Path("id") id: Int): Response<MovieImages>

}