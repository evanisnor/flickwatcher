package com.evanisnor.moviereminder

import android.app.Activity
import android.app.Application
import android.os.StrictMode
import com.evanisnor.moviereminder.cache.CacheComponent
import com.evanisnor.moviereminder.trendingmovies.TrendingMoviesActivity
import javax.inject.Singleton

@Singleton
class MovieReminderApp : Application() {

    lateinit var mainComponent: MainComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
        }

        val cacheComponent = CacheComponent.create(this)

        mainComponent = DaggerMainComponent.builder()
            .cacheComponent(cacheComponent)
            .context(this)
            .build()

        registerActivityLifecycleCallbacks(object : ActivityCallbacks() {
            override fun onActivityCreated(activity: Activity) {
                when (activity) {
                    is TrendingMoviesActivity -> mainComponent.inject(activity)
                }
            }
        })
    }
}