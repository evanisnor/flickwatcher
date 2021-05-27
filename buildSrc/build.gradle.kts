plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation("com.android.tools.build:gradle:7.0.0-beta02")
    implementation("com.android.tools.build:gradle-api:7.0.0-beta02")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
}
