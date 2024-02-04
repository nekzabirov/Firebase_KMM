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
            baseName = "firebase_app"
            isStatic = true
        }

        pod("FirebaseCore")  {
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:32.1.1"))
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