plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
}

val firebaseAuthPath = "${projectDir}/src/iosMain/libs/FirebaseAuth"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        publishAllLibraryVariants()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries {
            framework {
                version = libs.versions.project.get()
                baseName = "firebase_auth"
                isStatic = true
                linkerOpts.add("-framework FirebaseAuth -F$firebaseAuthPath")
                embedBitcode("disable")
            }
        }

        it.compilations.getByName("main").cinterops {
            val firebaseAuth by creating {
                defFile = file("$firebaseAuthPath/firebaseAuth.def")
                includeDirs(firebaseAuthPath)
                compilerOpts("-F$firebaseAuthPath")
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":firebase_app"))
        }

        androidMain.dependencies {
            implementation("com.google.firebase:firebase-auth")
        }
    }
}

android {
    namespace = "com.nekzabirov.firebaseapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

tasks {
    val embedArm64 by creating {
        val path = "${buildDir}/bin/iosArm64"

        copy {
            from(firebaseAuthPath)
            into("$path/debugFramework")
        }

        copy {
            from(firebaseAuthPath)
            into("$path/releaseFramework")
        }
    }

    val embedSimArm64 by creating {
        copy {
            from(firebaseAuthPath)
            into("${buildDir}/bin/iosSimulatorArm64/debugFramework")
        }
        copy {
            from(firebaseAuthPath)
            into("${buildDir}/bin/iosSimulatorArm64/releaseFramework")
        }
    }

    val embedSimX64 by creating {
        copy {
            from(firebaseAuthPath)
            into("${buildDir}/bin/iosX64/debugFramework")
        }

        copy {
            from(firebaseAuthPath)
            into("${buildDir}/bin/iosX64/releaseFramework")
        }
    }

    findByName("compileKotlinIosArm64")?.dependsOn(embedArm64)
    findByName("compileKotlinIosSimulatorArm64")?.dependsOn(embedSimArm64)
    findByName("compileKotlinIosX64")?.dependsOn(embedSimX64)
}