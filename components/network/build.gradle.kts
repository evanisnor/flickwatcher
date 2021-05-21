plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdk = 28
        targetSdk = 30
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
}

dependencies {
    implementation(projects.shared.model)

    implementation(Dependencies.Google.Dagger.dagger)
    kapt(Dependencies.Google.Dagger.daggerCompiler)

    implementation(Dependencies.Square.retrofit)
    implementation(Dependencies.Square.converterMoshi)
//    implementation(Dependencies.AndroidX.coreKtx)
//    implementation(Dependencies.AndroidX.appcompat)


    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.AndroidX.junitExt)
    androidTestImplementation(TestDependencies.AndroidX.espressoCore)
}