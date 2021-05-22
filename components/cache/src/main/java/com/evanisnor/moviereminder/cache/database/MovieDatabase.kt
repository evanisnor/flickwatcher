package com.evanisnor.moviereminder.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.evanisnor.moviereminder.cache.model.Movie

@Database(
    version = 1,
    entities = [
        Movie::class
    ]
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}