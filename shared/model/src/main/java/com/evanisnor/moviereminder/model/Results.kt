package com.evanisnor.moviereminder.model

data class Results(
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results: List<Movie>
)
