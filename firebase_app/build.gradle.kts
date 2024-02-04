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
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"

        framework {
            baseName = "firebase_app"
            isStatic = false
        }

        noPodspec()

        pod("FirebaseCore")  {
            extraOpts += listOf("-compiler-option", "-fmodules")
            git("https://github.com/firebase/firebase-ios-sdk.git")
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.ktx)
            implementation(libs.androidx.appcompat)
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