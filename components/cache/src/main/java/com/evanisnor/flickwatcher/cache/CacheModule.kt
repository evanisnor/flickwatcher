package com.evanisnor.flickwatcher.cache

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.evanisnor.flickwatcher.cache.database.MovieDao
import com.evanisnor.flickwatcher.cache.database.MovieDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
object CacheModule {

    @Provides
    @CacheScope
    fun cacheCoroutineScope() = CoroutineScope(Dispatchers.IO + SupervisorJob())

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
    @CacheScope
    fun dataStore(context: Context, cacheCoroutineScope: CoroutineScope) =
        PreferenceDataStoreFactory.create(
            corruptionHandler = null,
            migrations = listOf(),
            scope = cacheCoroutineScope
        ) {
            context.preferencesDataStoreFile("Cache")
        }

}