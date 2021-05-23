package com.evanisnor.flickwatcher.network.model

/**
 * Page
 *
 * https://developers.themoviedb.org/3/getting-started
 */
data class Page(
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results: List<Movie>
)
