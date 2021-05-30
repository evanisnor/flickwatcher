package com.evanisnor.gradle.semver

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.BufferedReader
import com.android.build.api.dsl.ApplicationExtension

/**
 * USAGE
 * gradle versions
 * gradle latestVersion
 * gradle currentVersion
 * gradle nextPatch
 * gradle nextMinor
 * gradle nextMajor
 * gradle nextCandidate
 * gradle releaseNextPatch
 * gradle releaseNextPatchCandidate
 * gradle releaseNextMinor
 * gradle releaseNextMinorCandidate
 * gradle releaseNextMajor
 * gradle releaseNextMajorCandidate
 * gradle releaseNextCandidate
 *
 * PARAMS
 * -Pdry-run
 */
class SemverPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val currentVersion = if (isCommitTagged()) {
            sortedVersions().first()
        } else {
            sortedVersions().first().nextPatch(isUntagged = true)
        }

        project.version = currentVersion.toString()
        project.extensions.findByType(ApplicationExtension::class.java)?.let {
            it.defaultConfig.versionName = currentVersion.toString()
            it.defaultConfig.versionCode = currentVersion.toInt()
        }

        project.tasks.register("versions", ListVersionsTask::class.java)
        project.tasks.register("latestVersion", LatestVersionTask::class.java)
        project.tasks.register("currentVersion", CurrentVersionTask::class.java)
        project.tasks.register("nextPatch", NextPatchTask::class.java)
        project.tasks.register("nextMinor", NextMinorTask::class.java)
        project.tasks.register("nextMajor", NextMajorTask::class.java)
        project.tasks.register("nextCandidate", NextCandidateTask::class.java)
        project.tasks.register("releaseNextPatch", ReleaseNextPatchTask::class.java)
        project.tasks.register("releaseNextPatchCandidate", ReleaseNextPatchCandidateTask::class.java)
        project.tasks.register("releaseNextMinor", ReleaseNextMinorTask::class.java)
        project.tasks.register("releaseNextMinorCandidate", ReleaseNextMinorCandidateTask::class.java)
        project.tasks.register("releaseNextMajor", ReleaseNextMajorTask::class.java)
        project.tasks.register("releaseNextMajorCandidate", ReleaseNextMajorCandidateTask::class.java)
        project.tasks.register("releaseNextCandidate", ReleaseNextCandidateTask::class.java)
    }
}

fun isDryRun(project: Project) = project.properties.contains("dry-run")

fun isCommitTagged() = runForExitCode("git describe --tags --exact-match") == 0

fun sortedVersions() = run("git tag")
    .map { it.toSemanticVersion() }
    .filter { it.isValid() }
    .sortedByDescending { it }

fun run(command: String) = ProcessBuilder()
    .command(command.split(" "))
    .start()
    .inputStream
    .bufferedReader()
    .use(BufferedReader::readLines)

fun runForExitCode(command: String) = ProcessBuilder()
    .command(command.split(" "))
    .start()
    .waitFor()

fun tagAndPush(project: Project, next: SemanticVersion) {
    if (isDryRun(project)) {
        println("**DRY RUN**")
        println("git tag $next")
        println("git push origin $next")
    } else {
        run("git tag $next")
        run("git push origin $next")
        println("$next released")
    }
}

fun String.toSemanticVersion() = SemanticVersionParser().parseSemVer(this)