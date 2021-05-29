package com.evanisnor.flickwatcher.build

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.*

abstract class FlickwatcherPlayStoreCredentialsTask : DefaultTask() {

    @TaskAction
    fun writePlayStoreCredentialsToDisk() {
        if (!project.hasPropertyAnywhere("flickwatcher.publish.credentials.outpath") &&
            (!project.hasPropertyAnywhere("flickwatcher.publish.credentials.base64") ||
                !project.hasPropertyAnywhere("flickwatcher.publish.credentials.filepath"))
        ) {
            println("Publishing properties not provided. Skipping.")
            return
        }

        val credentialsOutPath = project.stringProperty("flickwatcher.publish.credentials.outpath", "")
        val credentialsBase64 = project.stringProperty("flickwatcher.publish.credentials.base64", "")
        val filePath = project.stringProperty("flickwatcher.publish.credentials.filepath", "")

        if (filePath.isNotBlank()) {
            writeFromFile(filePath) {
                File(credentialsOutPath).apply {
                    writeBytes(it)
                }
            }
        } else if (credentialsBase64.isNotBlank()) {
            File(credentialsOutPath).apply {
                writeBytes(Base64.getDecoder().decode(credentialsBase64))
            }
        }

        println("Play Store credentials file written to $credentialsOutPath")
    }

    private fun writeFromFile(filePath: String, bytesOut: (ByteArray) -> Unit) {
        val credentialsFile = File(filePath)

        if (credentialsFile.exists()) {
            bytesOut(credentialsFile.readBytes())
        }
    }

}