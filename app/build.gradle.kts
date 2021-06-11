import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.evanisnor.flickwatcher.build.booleanProperty
import com.evanisnor.flickwatcher.build.stringProperty
import com.evanisnor.flickwatcher.build.versions.App
import com.evanisnor.flickwatcher.build.versions.Dependencies

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.evanisnor.flickwatcher.build")
    id("com.evanisnor.gradle.semver")
}

android {
    compileSdk = App.Android.compileSdk

    defaultConfig {
        applicationId = App.Android.packageName
        minSdk = App.Android.minSdk
        targetSdk = App.Android.targetSdk

        manifestPlaceholders["TheMovieDbApiKey"] = stringProperty("flickwatcher.apikey.themoviedb", "")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    if (booleanProperty("flickwatcher.signbuild", false)) {
        signingConfigs {
            create("release") {
                keyAlias = stringProperty("flickwatcher.keystore.alias")
                keyPassword = stringProperty("flickwatcher.keystore.password")
                storePassword = stringProperty("flickwatcher.keystore.password")
                storeFile = file(stringProperty("flickwatcher.keystore.filepath"))
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.findByName("release")
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

    lint {
        warning("Instantiatable")
        warning("ComposableLambdaParameterNaming")
        warning("ComposableLambdaParameterPosition")
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

tasks.register(
    "writeKeystoreFile",
    com.evanisnor.flickwatcher.build.FlickwatcherKeystoreTask::class
)

tasks.register(
    "writePlayStoreCredentials",
    com.evanisnor.flickwatcher.build.FlickwatcherPlayStoreCredentialsTask::class
)

tasks.register("publish", Exec::class) {
    commandLine("bundle", "exec", "fastlane", "supply",
        "--package_name", App.Android.packageName,
        "--aab", "build/outputs/bundle/release/app-release.aab",
        "--track", "alpha",
        "--release_status", "draft",
        "--json_key", "credentials.json"
    )
}.dependsOn("bundleRelease").dependsOn("writePlayStoreCredentials")

tasks.register("removeKeystoreFiles", Delete::class) {
    delete(fileTree(".").matching {
        include("**/*.jks")
    })
}

tasks.register("removeCredentialsFile", Delete::class) {
    delete(fileTree(".").matching {
        include("**/credentials.json")
    })
}

afterEvaluate {
    tasks.findByPath("validateSigningRelease")?.dependsOn("writeKeystoreFile")
    tasks.findByPath("clean")?.dependsOn("removeKeystoreFiles", "removeCredentialsFile")
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