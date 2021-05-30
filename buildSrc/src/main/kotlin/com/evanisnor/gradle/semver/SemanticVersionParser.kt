package com.evanisnor.gradle.semver

class SemanticVersionParser(
    private val prefix: String = "",
    private val candidatePrefix: String = "-RC.",
    private val versionPattern: String = "^$prefix^([0-9]+)\\.([0-9]+)\\.([0-9]+)(?:(?:$candidatePrefix)?([0-9]+)?(.*))?$"
) {

    fun parseSemVer(versionString: String): SemanticVersion {
        val groups = versionPattern.toRegex().find(versionString)?.groupValues
            ?: listOf("0", "0", "0", "0")

        return SemanticVersion(
            major = groups[1].toInt(),
            minor = groups[2].toInt(),
            patch = groups[3].toInt(),
            candidatePrefix = candidatePrefix,
            candidate = if (groups[4].isBlank()) 0 else groups[4].toInt(),
            isUntagged = false
        )
    }

}