import com.evanisnor.flickwatcher.build.versions.Dependencies

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.evanisnor.flickwatcher.library")
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
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.composeUi)
    implementation(Dependencies.AndroidX.composeMaterial)
    implementation(Dependencies.AndroidX.composeUiTooling)
}