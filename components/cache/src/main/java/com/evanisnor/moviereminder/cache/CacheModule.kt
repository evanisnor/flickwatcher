package com.evanisnor.moviereminder.cache

import android.content.Context
import androidx.room.Room
import com.evanisnor.moviereminder.cache.database.MovieDao
import com.evanisnor.moviereminder.cache.database.MovieDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
object CacheModule {

    @Provides
    @CacheScope
    fun dispatcher() = Dispatchers.IO

    @Provides
    @CacheScope
    fun movieDatabase(context: Context) = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        "movieDatabase"
    ).build()

    @Provides
    @CacheScope
    fun movieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }
}