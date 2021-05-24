package com.evanisnor.flickwatcher.cache.database

import androidx.room.*
import com.evanisnor.flickwatcher.cache.model.Movie
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie LIMIT :limit")
    fun getAllMovies(limit: Int): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE trendingDate >= :localDate")
    fun getTrendingMovies(localDate: LocalDate): Flow<List<Movie>>

    @Query("DELETE FROM Movie WHERE trendingDate < :localDate")
    fun clearOldTrendingMovies(localDate: LocalDate)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(vararg movies: Movie)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMovie(movie: Movie)

}