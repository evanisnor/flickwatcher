dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "moviereminder"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":components:network")
include(":components:cache")
include(":features:reminder")
