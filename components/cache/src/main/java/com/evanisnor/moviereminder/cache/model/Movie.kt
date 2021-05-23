package com.evanisnor.moviereminder.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey val id: Int = 0,
    val title: String,
    val overview: String = "",
    val releaseDate: String = "",
    val adult: Boolean = false,
    val video: Boolean = false,
    val popularity: Float = 0f,
    val trending: Boolean = false,
    val trendingRank: Int = -1
)
