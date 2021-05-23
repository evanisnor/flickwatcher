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
        // the shared 'maincomponent' module. This ensures that feature components can depend on
        // MainComponent and gain access to scoped global components such as Cache.
        mainComponent = DaggerMainComponent.builder()
            .cacheComponent(cacheComponent)
            .build()
    }
}