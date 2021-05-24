package com.evanisnor.flickwatcher.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDate
import com.evanisnor.flickwatcher.network.model.Movie as NetworkMovie

@Entity
data class Movie(
    @PrimaryKey val id: Int = 0,
    val title: String,
    val overview: String = "",
    val releaseDate: String = "",
    val adult: Boolean = false,
    val video: Boolean = false,
    val popularity: Float = 0f,
    val trendingDate: LocalDate? = null,
    val trendingRank: Int = -1,
    var posterUrl: String? = null,
    var backdropUrl: String? = null
)

fun NetworkMovie.toLocalTrending(index: Int) = Movie(
    id,
    title,
    overview,
    release_date,
    adult,
    video,
    popularity,
    trendingDate = LocalDate.now(),
    trendingRank = index + 1 // Offset by 1 so rank starts at 1 instead of 0
)