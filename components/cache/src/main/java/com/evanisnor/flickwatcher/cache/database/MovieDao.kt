package com.evanisnor.flickwatcher.cache.database

import androidx.room.*
import com.evanisnor.flickwatcher.cache.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie LIMIT :limit")
    fun getAllMovies(limit: Int): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE trending = 1")
    fun getTrendingMovies(): Flow<List<Movie>>

    @Query("DELETE FROM Movie WHERE trending = 1")
    fun deleteTrendingMovies()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(vararg movies: Movie)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMovie(movie: Movie)

}