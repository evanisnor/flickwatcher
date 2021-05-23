package com.evanisnor.flickwatcher.network

import com.evanisnor.flickwatcher.network.model.Page
import retrofit2.Response
import retrofit2.http.GET

interface TheMovieDbService {

    companion object {
        const val baseUrl = "https://api.themoviedb.org/"
    }

    @GET("/3/trending/movie/day")
    suspend fun getTrendingMoviesToday(): Response<Page>

}