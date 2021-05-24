package com.evanisnor.flickwatcher.network.model

/**
 * MovieImages
 *
 * https://developers.themoviedb.org/3/movies/get-movie-images
 */
data class MovieImages(
    val id: Int,
    val backdrops: List<MovieImage>,
    val posters: List<MovieImage>
)

data class MovieImage(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val width: Int,
    val vote_average: Float,
    val vote_count: Int
)