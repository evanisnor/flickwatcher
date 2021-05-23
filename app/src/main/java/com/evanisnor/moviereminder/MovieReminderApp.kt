package com.evanisnor.moviereminder

import android.os.StrictMode
import android.util.Log
import com.evanisnor.moviereminder.cache.CacheComponent
import com.evanisnor.moviereminder.libraries.maincomponent.ApplicationScope
import com.evanisnor.moviereminder.libraries.maincomponent.DaggerMainComponent
import com.evanisnor.moviereminder.libraries.maincomponent.MainApplication

@ApplicationScope
class MovieReminderApp : MainApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
        }

        val cacheComponent = CacheComponent.create(this)
        Log.i("AppScope", "Cache instance ${cacheComponent.getCache()}")

        mainComponent = DaggerMainComponent.builder()
            .cacheComponent(cacheComponent)
            .build()
    }
}