package com.evanisnor.flickwatcher.build

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project

fun Project.hasPropertyAnywhere(key: String) =
    properties.containsKey(key) || gradleLocalProperties(rootDir).containsKey(key)

fun Project.getPropertyAnywhere(key: String) = if (properties.containsKey(key)) {
    properties[key]
} else {
    gradleLocalProperties(rootDir)[key]
}

fun Project.stringProperty(key: String): String {
    val value = stringProperty(key, "")
    if (value.isBlank()) {
        throw Exception("Property $key is not defined")
    }
    return value
}

fun Project.stringProperty(key: String, defaultValue: String): String {
    val value = getPropertyAnywhere(key)
    return if (value is String && value.isNotBlank()) {
        value
    } else {
        defaultValue
    }
}

fun Project.booleanProperty(key: String): Boolean {
    if (!hasPropertyAnywhere(key)) {
        throw Exception("Property $key is not defined")
    }

    val value = getPropertyAnywhere(key)

    return if (value is String && value.isValidBoolean()) {
        value.toBoolean()
    } else {
        throw Exception("Value of property $key cannot be cast to boolean: $value")
    }
}

fun Project.booleanProperty(key: String, defaultValue: Boolean): Boolean {
    val value = getPropertyAnywhere(key)

    return if (value is String && value.isValidBoolean()) {
        value.toBoolean()
    } else {
        defaultValue
    }
}

fun String.isValidBoolean() = isNotBlank() && (this == "true" || this == "false")