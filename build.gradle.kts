// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.6.0" apply false
}

ktlint {
    version = "0.42.1" // Use the desired ktlint version
    ignoreFailures = false // Set to true if you want to continue even if there are ktlint issues
    reporters {
        reporter("plain")
        reporter("checkstyle")
    }
}
