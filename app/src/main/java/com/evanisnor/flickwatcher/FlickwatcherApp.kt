package com.evanisnor.flickwatcher

import android.os.StrictMode
import com.evanisnor.flickwatcher.cache.CacheComponent
import com.evanisnor.flickwatcher.maincomponent.ApplicationScope
import com.evanisnor.flickwatcher.maincomponent.DaggerMainComponent
import com.evanisnor.flickwatcher.maincomponent.MainApplication

@ApplicationScope
class FlickwatcherApp : MainApplication() {

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