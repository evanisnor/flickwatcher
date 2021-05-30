package com.evanisnor.gradle.semver

class SemanticVersion(
    val major: Int,
    val minor: Int,
    val patch: Int,
    val candidatePrefix: String,
    val candidate: Int = 0,
    val isUntagged: Boolean = false,
    val untaggedSuffix: String = "-SNAPSHOT"
) : Comparable<SemanticVersion> {

    fun nextMajor() = SemanticVersion(
        major + 1,
        0,
        0,
        candidatePrefix,
        candidate = 0)

    fun nextMinor() = SemanticVersion(
        major,
        minor + 1,
        0,
        candidatePrefix,
        candidate = 0)

    fun nextPatch(isUntagged: Boolean = false) = SemanticVersion(
        major,
        minor,
        patch + 1,
        candidatePrefix,
        0,
        isUntagged = isUntagged)

    fun nextCandidate() = SemanticVersion(
        major,
        minor,
        patch,
        candidatePrefix,
        candidate = candidate + 1)

    fun nextVersion() = if (candidate > 0) {
        nextCandidate()
    } else {
        nextPatch(isUntagged = true)
    }

    fun isValid() = major >= 0 && minor in 0..999 && patch in 0..999 && candidate in 0..999

    fun toInt(): Int =
        Integer.parseInt(
            "$major" +
                minor.toString().padStart(3, '0') +
                patch.toString().padStart(3, '0') +
                candidate.toString().padStart(3, '0'))

    override fun toString(): String {
        val versionString = if (candidate > 0) {
            "$major.$minor.$patch$candidatePrefix$candidate"
        } else {
            "$major.$minor.$patch"
        }

        return if (isUntagged) {
            "$versionString$untaggedSuffix"
        } else {
            versionString
        }
    }

    override fun compareTo(other: SemanticVersion) = toInt() - other.toInt()
}