import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.evanisnor.flickwatcher.build.versions.Build
import com.evanisnor.flickwatcher.build.versions.Dependencies

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.evanisnor.flickwatcher.build")
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

    val properties = if (project.properties.containsKey("flickwatcher.signbuild")) {
        project.properties
    } else {
        gradleLocalProperties(rootDir)
    }

    if (properties.containsKey("flickwatcher.signbuild")) {
        signingConfigs {
            create("release") {
                keyAlias = properties["flickwatcher.keystore.alias"] as String
                keyPassword = properties["flickwatcher.keystore.password"] as String
                storePassword = properties["flickwatcher.keystore.password"] as String
                storeFile = file(properties["flickwatcher.keystore.filepath"] as String)
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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

tasks.register("removeKeystoreFiles", Delete::class) {
    delete(fileTree(".").matching {
        include("**/*.jks")
    })
}

afterEvaluate {
    tasks.findByPath("validateSigningRelease")?.dependsOn("writeKeystoreFile")
    tasks.findByPath("clean")?.dependsOn("removeKeystoreFiles")
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