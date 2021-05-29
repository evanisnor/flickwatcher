package com.evanisnor.flickwatcher.build

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project


fun Project.stringProperty(key: String): String {
    val property = if (properties.containsKey(key)) {
        properties[key]
    } else {
        gradleLocalProperties(rootDir)[key]
    }

    if (property is String && property.isNotBlank()) {
        return property
    }

    throw Exception("Property $key is not defined")
}

fun Project.booleanProperty(key: String): Boolean {
    val property = if (properties.containsKey(key)) {
        properties[key]
    } else {
        gradleLocalProperties(rootDir)[key]
    }

    if (property is String && property.isNotBlank() && (
            property == "true" || property == "false")) {
        return property.toBoolean()
    }

    throw Exception("Property $key is not defined")
}