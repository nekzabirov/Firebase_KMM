plugins {
    id("com.android.library").version("8.1.0-rc01").apply(false)
    kotlin("multiplatform").version("1.9.0").apply(false)
    id("com.google.gms.google-services").version("4.3.15").apply(false)
    kotlin("plugin.serialization").version("1.9.0").apply(false)
    id("maven-publish")
}

subprojects {
    this.plugins.apply("maven-publish")

    this.publishing {
        publications {}

        repositories {
            mavenLocal()

            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/nekzabirov/Firebase_KMM")

                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                    password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN")
                }
            }
        }
    }

    this.group = "com.nekzabirov.firebase"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
    subprojects {
        delete(buildDir)
    }
}

tasks.register("publishAllToLocalMaven") {
    dependsOn(subprojects.mapNotNull { it.tasks["publishToMavenLocal"] })
}