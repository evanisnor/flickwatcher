buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.Android.gradlePlugin)
        classpath(Build.Kotlin.gradlePlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
