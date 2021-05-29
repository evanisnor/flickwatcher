package com.evanisnor.flickwatcher.build.versions

object TestDependencies {

    const val junit = "junit:junit:4.13.2"

    object AndroidX {
        const val junitExt = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
        const val composeUiTest =
            "androidx.compose.ui:ui-test-junit4:${Dependencies.AndroidX.composeVersion}"
    }

}