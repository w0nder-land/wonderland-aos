pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}

rootProject.name = "Wonderland"
include(":app")
include(":domain")
include(":data")
include(":common:base")
include(":common:model")
include(":common:auth")
include(":common:component")
include(":common:resource")
include(":feature:splash")
include(":feature:onboarding")
include(":feature:festival")
