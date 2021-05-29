import com.evanisnor.flickwatcher.build.versions.Dependencies
import com.evanisnor.flickwatcher.build.versions.TestDependencies

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.evanisnor.flickwatcher.build")
}

flickwatcherLibrary {
}

dependencies {
    implementation(Dependencies.Google.Dagger.dagger)
    kapt(Dependencies.Google.Dagger.daggerCompiler)

    implementation(Dependencies.Square.retrofit)
    implementation(Dependencies.Square.converterMoshi)
    implementation(Dependencies.Jetbrains.coroutinesCore)
    implementation(Dependencies.Coil.coilBase)

    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.AndroidX.junitExt)
    androidTestImplementation(TestDependencies.AndroidX.espressoCore)
}