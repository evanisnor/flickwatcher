package com.evanisnor.flickwatcher.network.model

/**
 * Movie
 *
 * https://developers.themoviedb.org/3/movies/get-movie-details
 */
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val genre_ids: List<Int>,
    val adult: Boolean,

    val poster_path: String,
    val backdrop_path: String,
    val video: Boolean,

    val original_language: String,
    val original_title: String,

    val popularity: Float,
    val vote_average: Float,
    val vote_count: Int
)
