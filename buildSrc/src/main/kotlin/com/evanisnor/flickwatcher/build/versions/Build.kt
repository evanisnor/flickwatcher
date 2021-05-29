package com.evanisnor.flickwatcher.build.versions

object Build {

    object Flickwatcher {
        const val versionCode = 1
        const val versionName = "1.0"
    }

    object Android {
        const val minSdk = 28
        const val compileSdk = 30
        const val targetSdk = compileSdk
        const val gradlePlugin = "com.android.tools.build:gradle:7.0.0-beta03"
    }

    object Kotlin {
        const val gradlePlugin = "org.jetbrains.java:java-gradle-plugin:1.4.32"
    }

}