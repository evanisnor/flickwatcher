import com.evanisnor.flickwatcher.build.versions.Dependencies
import com.evanisnor.flickwatcher.build.versions.TestDependencies

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.evanisnor.flickwatcher.build")
}

flickwatcherLibrary {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.AndroidX.composeVersion
    }
}

dependencies {
    implementation(projects.components.maincomponent)
    implementation(projects.components.cache)
    implementation(projects.libraries.viewmodelfactory)
    implementation(projects.libraries.uxComposable)

    implementation(Dependencies.Google.Dagger.dagger)
    kapt(Dependencies.Google.Dagger.daggerCompiler)

    implementation(Dependencies.Jetbrains.coroutinesCore)
    implementation(Dependencies.Jetbrains.coroutinesAndroid)

    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.lifecycleRuntimeKtx)
    implementation(Dependencies.AndroidX.lifecycleLiveDataKtx)
    implementation(Dependencies.AndroidX.composeLiveData)
    implementation(Dependencies.AndroidX.composeUi)
    implementation(Dependencies.AndroidX.composeMaterial)
    implementation(Dependencies.AndroidX.composeUiTooling)
    implementation(Dependencies.AndroidX.activityCompose)
    implementation(Dependencies.Google.accompanist)

    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.AndroidX.junitExt)
    androidTestImplementation(TestDependencies.AndroidX.espressoCore)
    androidTestImplementation(TestDependencies.AndroidX.composeUiTest)

}