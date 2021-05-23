package com.evanisnor.moviereminder

import android.os.StrictMode
import com.evanisnor.moviereminder.cache.CacheComponent
import com.evanisnor.moviereminder.maincomponent.ApplicationScope
import com.evanisnor.moviereminder.maincomponent.DaggerMainComponent
import com.evanisnor.moviereminder.maincomponent.MainApplication

@ApplicationScope
class MovieReminderApp : MainApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
        }

        val cacheComponent = CacheComponent.create(this)

        // MainComponent is exposed to other gradle dependencies via MainApplication in
        // the 'maincomponent' module. This ensures that feature components can depend on
        // MainComponent.
        mainComponent = DaggerMainComponent.builder()
            .cacheComponent(cacheComponent)
            .build()
    }
}