import java.net.URI

include(":testresource")


pluginManagement {
    repositories {
        maven { setUrl("https://repo1.maven.org/maven2") }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { setUrl("https://repo1.maven.org/maven2") }
        google()
        mavenCentral()
    }
}

rootProject.name = "elog"
include(":sample")
include(":elog")
