package com.evanisnor.flickwatcher.build.versions

object Dependencies {

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.5.0"
        const val appcompat = "androidx.appcompat:appcompat:1.3.0"

        private const val lifecycleVersion = "2.3.1"
        const val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val lifecycleLiveDataKtx =
            "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        const val lifecycleViewModelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"

        private const val roomVersion = "2.3.0"
        const val room = "androidx.room:room-runtime:$roomVersion"
        const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
        const val roomKtx = "androidx.room:room-ktx:$roomVersion"

        const val datastorePreferences = "androidx.datastore:datastore-preferences:1.0.0-beta01"

        const val composeVersion = "1.0.0-beta08"
        const val composeUi = "androidx.compose.ui:ui:$composeVersion"
        const val composeMaterial = "androidx.compose.material:material:$composeVersion"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
        const val composeFoundation ="androidx.compose.foundation:foundation:$composeVersion"
        const val activityCompose = "androidx.activity:activity-compose:1.3.0-beta01"
        const val constraintLayoutCompose = "androidx.constraintlayout:constraintlayout-compose:1.0.0-alpha07"
    }

    object Google {
        const val accompanist = "com.google.accompanist:accompanist-coil:0.11.1"

        object Dagger {
            private const val daggerVersion = "2.35.1"
            const val dagger = "com.google.dagger:dagger:$daggerVersion"
            const val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
        }
    }

    object Square {
        private const val retrofitVersion = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val converterMoshi = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"

        const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.7"
    }

    object Jetbrains {
        private const val coroutinesVersion = "1.5.0"
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    }

    object Coil {
        const val coilBase = "io.coil-kt:coil-base:1.2.2"
    }

}