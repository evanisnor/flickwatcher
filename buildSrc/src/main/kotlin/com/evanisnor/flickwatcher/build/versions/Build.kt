package com.evanisnor.flickwatcher.build.versions

object Build {

    object Flickwatcher {
        const val versionCode = 2
        const val versionName = "0.0.1"
    }

    object Android {
        const val packageName = "com.evanisnor.flickwatcher"
        const val minSdk = 28
        const val compileSdk = 30
        const val targetSdk = compileSdk
        const val gradlePlugin = "com.android.tools.build:gradle:7.0.0-beta03"
    }

    object Kotlin {
        const val gradlePlugin = "org.jetbrains.java:java-gradle-plugin:1.4.32"
    }

}