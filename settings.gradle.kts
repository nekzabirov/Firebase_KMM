pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "FirebaseApp"
include(":firebase_app")
include(":firebase_auth")
include(":firebase_storage")
include(":firebase_firestore")
include(":firebase_functions")