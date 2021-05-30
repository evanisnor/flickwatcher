package com.evanisnor.gradle.semver

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


open class ListVersionsTask : DefaultTask() {

    @TaskAction
    fun versions() {
        sortedVersions().forEach {
            println(it)
        }
    }

}

open class LatestVersionTask : DefaultTask() {

    @TaskAction
    fun latestVersion() {
        println(sortedVersions().first())
    }
}

open class CurrentVersionTask : DefaultTask() {

    @TaskAction
    fun currentVersion() {
        val currentVersion = if (isCommitTagged()) {
            sortedVersions().first()
        } else {
            sortedVersions().first().nextVersion()
        }

        println("$currentVersion (${currentVersion.toInt()})")
    }
}

open class NextPatchTask : DefaultTask() {

    @TaskAction
    fun nextPatch() {
        val next = sortedVersions().first().nextPatch()
        println("$next (${next.toInt()})")
    }
}

open class NextMinorTask : DefaultTask() {

    @TaskAction
    fun nextMinor() {
        val next = sortedVersions().first().nextMinor()
        println("$next (${next.toInt()})")
    }
}

open class NextMajorTask : DefaultTask() {

    @TaskAction
    fun nextMajor() {
        val next = sortedVersions().first().nextMajor()
        println("$next (${next.toInt()})")
    }
}

open class NextCandidateTask : DefaultTask() {

    @TaskAction
    fun nextCandidate() {
        val next = sortedVersions().first().nextCandidate()
        println("$next (${next.toInt()})")
    }
}

open class ReleaseNextPatchTask : DefaultTask() {

    @TaskAction
    fun releasePatch() {
        val next = sortedVersions().first().nextPatch()
        tagAndPush(project, next)
    }
}

open class ReleaseNextPatchCandidateTask : DefaultTask() {

    @TaskAction
    fun releasePatch() {
        val next = sortedVersions().first().nextPatch().nextCandidate()
        tagAndPush(project, next)
    }
}

open class ReleaseNextMinorTask : DefaultTask() {

    @TaskAction
    fun releaseMinor() {
        val next = sortedVersions().first().nextMinor()
        tagAndPush(project, next)
    }
}

open class ReleaseNextMinorCandidateTask : DefaultTask() {

    @TaskAction
    fun releaseMinor() {
        val next = sortedVersions().first().nextMinor().nextCandidate()
        tagAndPush(project, next)
    }
}

open class ReleaseNextMajorTask : DefaultTask() {

    @TaskAction
    fun releaseMajor() {
        val next = sortedVersions().first().nextMajor()
        tagAndPush(project, next)
    }
}

open class ReleaseNextMajorCandidateTask : DefaultTask() {

    @TaskAction
    fun releaseMajor() {
        val next = sortedVersions().first().nextMajor().nextCandidate()
        tagAndPush(project, next)
    }
}

open class ReleaseNextCandidateTask : DefaultTask() {

    @TaskAction
    fun releaseCandidate() {
        val next = sortedVersions().first().nextCandidate()
        tagAndPush(project, next)
    }
}