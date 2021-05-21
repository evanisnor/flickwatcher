buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.Android.gradlePlugin)
        classpath(Build.Kotlin.gradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}