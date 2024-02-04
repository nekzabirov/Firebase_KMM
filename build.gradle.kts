plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    id("maven-publish")
}

subprojects {
    version = rootProject.libs.versions.project.get()

    plugins.apply("maven-publish")

    publishing {
        publications {}

        repositories {
            mavenLocal()

            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/nekzabirov/Firebase_KMM")

                credentials {
                    username = System.getenv("GITHUB_USERNAME")
                    password = System.getenv("GITHUB_WRITE_TOKEN")
                }
            }
        }
    }

    group = "com.nekzabirov.firebase"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
    subprojects {
        delete(buildDir)
    }
}

tasks.register("publishAll") {
    dependsOn(subprojects.mapNotNull { it.tasks["publish"] })
}

tasks.register("publishLocalAll") {
    dependsOn(subprojects.mapNotNull { it.tasks["publishToMavenLocal"] })
}