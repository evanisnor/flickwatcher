package com.evanisnor.flickwatcher.build

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.*

abstract class FlickwatcherKeystoreTask : DefaultTask() {

    @TaskAction
    fun writeKeystoreFileToDisk() {
        if (!project.hasProperty("flickwatcher.keystore.outpath") ||
            !project.hasProperty("flickwatcher.keystore.base64")
        ) {
            println("Keystore properties not provided. Skipping.")
            return
        }

        val keystoreOutPath = project.properties["flickwatcher.keystore.outpath"] as String
        val keystoreBase64 = project.properties["flickwatcher.keystore.base64"] as String

        File(keystoreOutPath).apply {
            writeBytes(Base64.getDecoder().decode(keystoreBase64))
        }

        println("Keystore file written to $keystoreOutPath")
    }

}