package com.evanisnor.moviereminder

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Wrapper class to stub out required functions so I don't have to look at them if I don't need them.
 */
abstract class ActivityCallbacks : Application.ActivityLifecycleCallbacks {

    open fun onActivityCreated(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        onActivityCreated(activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

}