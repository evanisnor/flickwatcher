import com.evanisnor.flickwatcher.build.versions.Dependencies
import com.evanisnor.flickwatcher.build.versions.TestDependencies

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.evanisnor.flickwatcher.library")
}

flickwatcherLibrary {
}

dependencies {
    api(projects.components.network)

    implementation(Dependencies.Google.Dagger.dagger)
    kapt(Dependencies.Google.Dagger.daggerCompiler)

    implementation(Dependencies.Square.retrofit)
    implementation(Dependencies.Jetbrains.coroutinesCore)
    implementation(Dependencies.Jetbrains.coroutinesAndroid)

    implementation(Dependencies.AndroidX.datastorePreferences)
    implementation(Dependencies.AndroidX.room)
    implementation(Dependencies.AndroidX.roomKtx)
    kapt(Dependencies.AndroidX.roomCompiler)

    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.AndroidX.junitExt)
    androidTestImplementation(TestDependencies.AndroidX.espressoCore)
}