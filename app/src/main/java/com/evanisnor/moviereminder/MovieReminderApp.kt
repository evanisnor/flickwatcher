package com.evanisnor.moviereminder

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.StrictMode
import com.evanisnor.moviereminder.cache.Cache
import com.evanisnor.moviereminder.cache.CacheComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieReminderApp : Application() {

    @Inject
    lateinit var cache: Cache

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
        }

        val mainComponent = DaggerMainComponent.builder()
            .cacheComponent(
                // This seems like a hack to avoid adding a gradle dependency
                // on the Network module.
                CacheComponent.create(this)
            ).build()

        mainComponent.inject(this)

        registerActivityLifecycleCallbacks(object : ActivityCallbacks() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (activity is MainActivity) {
                    mainComponent.inject(activity)
                }
            }
        })
    }
}