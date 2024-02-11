rootProject.name = "Demo"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenLocal()
        maven {
            url = uri("https://maven.pkg.github.com/nekzabirov/KMM-Compose-Navigation")
            url = uri("https://maven.pkg.github.com/nekzabirov/Viewmodel_KMM")
            url = uri("https://maven.pkg.github.com/nekzabirov/Firebase_KMM")
        }
    }
}

include(":composeApp")