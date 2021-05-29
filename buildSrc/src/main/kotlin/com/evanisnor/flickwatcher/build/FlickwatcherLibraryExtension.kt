package com.evanisnor.flickwatcher.build

import com.android.build.api.dsl.LibraryExtension
import com.evanisnor.flickwatcher.build.versions.Build
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class FlickwatcherLibraryExtension(
    private val libraryExtension: LibraryExtension
) : LibraryExtension by libraryExtension {

    init {
        compileSdk = Build.Android.compileSdk

        defaultConfig {
            minSdk = Build.Android.minSdk
            targetSdk = Build.Android.targetSdk
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
                    "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
                )
        }
    }
}