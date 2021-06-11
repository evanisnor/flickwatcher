buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val kotlin = "1.5.10"
        val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin"

        val android = "7.0.0-beta03"
        val androidGradlePlugin = "com.android.tools.build:gradle:$android"

        classpath(kotlinGradlePlugin)
        classpath(androidGradlePlugin)
    }
}
