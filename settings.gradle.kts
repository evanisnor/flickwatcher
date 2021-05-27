dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "flickwatcher"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":components:network")
include(":components:cache")
include(":features:trendingmovies")
include(":components:maincomponent")
include(":libraries:viewmodelfactory")
include(":libraries:ux-composable")
