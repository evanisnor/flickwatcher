package com.evanisnor.moviereminder.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val adult: Boolean,
    val video: Boolean,
    val popularity: Float,
    val trending: Boolean,
    val trendingRank: Int
)
