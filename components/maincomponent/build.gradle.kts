import com.evanisnor.flickwatcher.build.versions.Dependencies

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.evanisnor.flickwatcher.build")
}

flickwatcherLibrary {
}

dependencies {
    // Global Components
    implementation(projects.components.cache)

    // AndroidX
    implementation(Dependencies.AndroidX.activityCompose)

    // Dagger
    implementation(Dependencies.Google.Dagger.dagger)
    kapt(Dependencies.Google.Dagger.daggerCompiler)
}