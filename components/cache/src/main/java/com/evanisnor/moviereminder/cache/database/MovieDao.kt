package com.evanisnor.moviereminder.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.evanisnor.moviereminder.cache.model.Movie
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


}