plugins {
    id("com.android.library").version("8.1.0-rc01").apply(false)
    kotlin("multiplatform").version("1.9.0").apply(false)
    id("com.google.gms.google-services").version("4.3.15").apply(false)
    kotlin("plugin.serialization").version("1.9.0").apply(false)
    id("maven-publish")
}

subprojects {
    this.version = "1.0.0"

    this.plugins.apply("maven-publish")

    this.publishing {
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

    this.group = "com.nekzabirov.firebase"
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