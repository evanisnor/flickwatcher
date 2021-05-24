package com.evanisnor.flickwatcher.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.evanisnor.flickwatcher.cache.model.Movie

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        Movie::class
    ]
)
@TypeConverters(CustomTypeConverters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}