package com.evanisnor.moviereminder

import android.app.Application
import android.util.Log
import com.evanisnor.moviereminder.cache.DaggerCacheComponent

class MovieReminderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DaggerMainComponent.create().inject(this)
        DaggerCacheComponent.create().getCache().loadTrendingMovies {
            for (movie in it) {
                Log.i("MovieReminderApp", movie.title)
            }
        }
    }
}