plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

version = "1.0.1"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

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
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"

        pod("FirebaseStorage")

        framework {
            baseName = "firebase_firestorage"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":firebase_app"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("com.google.firebase:firebase-storage-ktx")
            }
        }
    }
}

android {
    namespace = "com.nekzabirov.firestorage"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
    }
}