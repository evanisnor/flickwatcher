package com.evanisnor.flickwatcher.build

import com.android.build.api.dsl.LibraryExtension
import com.evanisnor.flickwatcher.build.versions.App
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class FlickwatcherLibraryExtension(
    private val libraryExtension: LibraryExtension
) : LibraryExtension by libraryExtension {

    init {
        compileSdk = App.Android.compileSdk

        defaultConfig {
            minSdk = App.Android.minSdk
            targetSdk = App.Android.targetSdk
            consumerProguardFiles("consumer-rules.pro")
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

        (libraryExtension as ExtensionAware).configure<KotlinJvmOptions> {
            jvmTarget = "1.8"
            freeCompilerArgs =
                listOf(
                    *freeCompilerArgs.toTypedArray(),
                    "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-Xuse-experimental=androidx.compose.runtime.ExperimentalComposeApi"
                )
        }
    }
}
