pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        jcenter()
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io" ) }
    }
}
rootProject.name = "MuziMusicApp"
include(":app")
 