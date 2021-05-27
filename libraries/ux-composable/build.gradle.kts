plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = GlobalVersion.compose
    }
}

dependencies {
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.composeUi)
    implementation(Dependencies.AndroidX.composeMaterial)
    implementation(Dependencies.AndroidX.composeUiTooling)
}