plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    kotlin("native.cocoapods")
}

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

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = libs.versions.project.get()
        ios.deploymentTarget = libs.versions.iosDeploymentTarget.get()

        framework {
            baseName = "firebase_auth"
            isStatic = false
        }

        pod("FirebaseAuth")  {
            extraOpts += listOf("-compiler-option", "-fmodules")
            git("https://github.com/firebase/firebase-ios-sdk.git")
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