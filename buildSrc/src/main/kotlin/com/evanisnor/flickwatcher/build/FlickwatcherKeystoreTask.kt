package com.evanisnor.flickwatcher.build

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.*

abstract class FlickwatcherKeystoreTask : DefaultTask() {

    @TaskAction
    fun writeKeystoreFileToDisk() {
        if (!project.hasPropertyAnywhere("flickwatcher.keystore.outpath") ||
            !project.hasPropertyAnywhere("flickwatcher.keystore.base64")
        ) {
            println("Keystore properties not provided. Skipping.")
            return
        }

        val keystoreOutPath = project.stringProperty("flickwatcher.keystore.outpath")
        val keystoreBase64 = project.stringProperty("flickwatcher.keystore.base64")

        File(keystoreOutPath).apply {
            writeBytes(Base64.getDecoder().decode(keystoreBase64))
        }

        println("Keystore file written to $keystoreOutPath")
    }

}