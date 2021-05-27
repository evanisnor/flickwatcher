import com.evanisnor.flickwatcher.build.versions.Build
import com.evanisnor.flickwatcher.build.versions.Dependencies

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = Build.Android.compileSdk

    defaultConfig {
        applicationId = "com.evanisnor.flickwatcher"
        minSdk = Build.Android.minSdk
        targetSdk = Build.Android.targetSdk

        versionCode = Build.Flickwatcher.versionCode
        versionName = Build.Flickwatcher.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs =
            listOf(
                *freeCompilerArgs.toTypedArray(),
                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
            )
    }
}

dependencies {
    // Global Components
    implementation(projects.components.maincomponent)
    implementation(projects.components.cache)

    // Features
    implementation(projects.features.trendingmovies)

    // Dagger
    implementation(Dependencies.Google.Dagger.dagger)
    kapt(Dependencies.Google.Dagger.daggerCompiler)

    // Debug
    debugImplementation(Dependencies.Square.leakcanary)
}