package com.evanisnor.moviereminder

import android.app.Application
import android.util.Log
import com.evanisnor.moviereminder.cache.Cache
import com.evanisnor.moviereminder.cache.DaggerCacheComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieReminderApp : Application() {

    @Inject
    lateinit var cache: Cache

    override fun onCreate() {
        super.onCreate()

        DaggerMainComponent.builder()
            .cacheComponent(
                DaggerCacheComponent.create()
            ).build()
            .inject(this)

        cache.loadTrendingMovies {
            for (movie in it) {
                Log.i("MovieReminderApp", movie.title)
            }
        }
    }
}