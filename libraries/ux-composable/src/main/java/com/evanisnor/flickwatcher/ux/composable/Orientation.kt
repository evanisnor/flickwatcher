package com.evanisnor.flickwatcher.ux.composable

import android.content.res.Configuration

fun Int.isLandscape(): Boolean = this == Configuration.ORIENTATION_LANDSCAPE
fun Int.isPortrait(): Boolean = this == Configuration.ORIENTATION_PORTRAIT