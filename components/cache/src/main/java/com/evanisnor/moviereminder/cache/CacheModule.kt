package com.evanisnor.moviereminder.cache

import android.content.Context
import androidx.room.Room
import com.evanisnor.moviereminder.cache.database.MovieDao
import com.evanisnor.moviereminder.cache.database.MovieDatabase
import com.evanisnor.moviereminder.network.DaggerNetworkComponent
import com.evanisnor.moviereminder.network.NetworkComponent
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
object CacheModule {

    @Provides
    fun dispatcher() = Dispatchers.IO

    @Provides
    fun networkComponent(): NetworkComponent = DaggerNetworkComponent.create()

    @Provides
    fun theMovieDbService(networkComponent: NetworkComponent) =
        networkComponent.getTheMovieDbController()

    @Provides
    fun movieDatabase(context: Context) = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        "movieDatabase"
    ).build()

    @Provides
    fun movieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }
}