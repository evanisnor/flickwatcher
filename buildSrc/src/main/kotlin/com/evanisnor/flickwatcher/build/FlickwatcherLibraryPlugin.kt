package com.evanisnor.flickwatcher.build

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class FlickwatcherLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.findByType(LibraryExtension::class.java)?.let { libraryExtension ->
            project.extensions.add(
                "flickwatcherLibrary",
                FlickwatcherLibraryExtension(libraryExtension)
            )
        }
    }
}
