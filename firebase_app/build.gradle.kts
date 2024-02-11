import org.jetbrains.kotlin.util.capitalizeDecapitalize.toUpperCaseAsciiOnly

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
}

val firebaseCorePath = "${projectDir}/src/iosMain/libs/FirebaseCore"

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
                baseName = "FirebaseApp"
                isStatic = true
                val nPath = "/Users/nekzabirov/Documents/NekCapital/Firebase_KMM/firebase_app/src/iosMain/libs/FirebaseCore"
                linkerOpts("-F$nPath", "-framework FirebaseCore")
                embedBitcode("disable")
            }
        }

        it.compilations.getByName("main").cinterops {
            val firebaseCore by creating {
                defFile = file("$firebaseCorePath/firebaseCore.def")
                includeDirs(firebaseCorePath)
                compilerOpts("-F$firebaseCorePath")
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:32.1.1"))
            //noinspection UseTomlInstead
            implementation("com.google.firebase:firebase-common")
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
            from(firebaseCorePath)
            into("$path/debugFramework")
        }

        copy {
            from(firebaseCorePath)
            into("$path/releaseFramework")
        }
    }

    val embedSimArm64 by creating {
        copy {
            from(firebaseCorePath)
            into("${buildDir}/bin/iosSimulatorArm64/debugFramework")
        }
        copy {
            from(firebaseCorePath)
            into("${buildDir}/bin/iosSimulatorArm64/releaseFramework")
        }
    }

    val embedSimX64 by creating {
        copy {
            from(firebaseCorePath)
            into("${buildDir}/bin/iosX64/debugFramework")
        }

        copy {
            from(firebaseCorePath)
            into("${buildDir}/bin/iosX64/releaseFramework")
        }
    }

    findByName("compileKotlinIosArm64")?.dependsOn(embedArm64)
    findByName("compileKotlinIosSimulatorArm64")?.dependsOn(embedSimArm64)
    findByName("compileKotlinIosX64")?.dependsOn(embedSimX64)
}
