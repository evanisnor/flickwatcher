package com.evanisnor.flickwatcher.cache

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.evanisnor.flickwatcher.cache.database.MovieDao
import com.evanisnor.flickwatcher.cache.database.MovieDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

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

    @Provides
    @Named("ConfigurationSharedPrefs")
    fun sharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("Configuration", MODE_PRIVATE)

}