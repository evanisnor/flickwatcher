buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(com.evanisnor.flickwatcher.build.versions.Build.Android.gradlePlugin)
        classpath(com.evanisnor.flickwatcher.build.versions.Build.Kotlin.gradlePlugin)
    }
}
