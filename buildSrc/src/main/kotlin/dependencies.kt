object GlobalVersion {
    const val compose = "1.0.0-beta07"
}

object Build {

    object Android {
        const val minSdk = 28
        const val compileSdk = 30
        const val targetSdk = compileSdk
        const val gradlePlugin = "com.android.tools.build:gradle:7.0.0-beta02"
    }

    object Kotlin {
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32"
    }

}

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

        const val composeUi = "androidx.compose.ui:ui:${GlobalVersion.compose}"
        const val composeMaterial = "androidx.compose.material:material:${GlobalVersion.compose}"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:${GlobalVersion.compose}"
        const val composeLiveData =
            "androidx.compose.runtime:runtime-livedata:${GlobalVersion.compose}"
        const val activityCompose = "androidx.activity:activity-compose:1.3.0-alpha08"
    }

    object Google {
        const val material = "com.google.android.material:material:1.3.0"
        const val accompanist = "com.google.accompanist:accompanist-coil:0.10.0"

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

}

object TestDependencies {

    const val junit = "junit:junit:4.13.2"

    object AndroidX {
        const val junitExt = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
        const val composeUiTest = "androidx.compose.ui:ui-test-junit4:${GlobalVersion.compose}"
    }

}