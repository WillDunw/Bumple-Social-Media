pluginManagement {
plugins {
  id("org.jlleitschuh.gradle.ktlint") version "<current_version>"
}
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
    }
}

rootProject.name = "EmptyActivity"
include(":app")
