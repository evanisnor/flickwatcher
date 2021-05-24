package com.evanisnor.flickwatcher.maincomponent

import androidx.activity.ComponentActivity

/**
 * I don't like BaseActivity classes, but this one is here to avoid a memory leak.
 *
 * https://issuetracker.google.com/issues/139738913
 */
open class FlickwatcherActivity : ComponentActivity() {

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }

}
