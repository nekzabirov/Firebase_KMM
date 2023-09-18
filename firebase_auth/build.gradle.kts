plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}


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

        framework {
            baseName = "firebase_auth"
            isStatic = true
            export(project(":firebase_app"))
        }

        pod("FirebaseAuth") { version = "10.15.0" }
        pod("GoogleSignIn") { version = "7.0.0" }
        pod("FBAEMKit")
        pod("FBSDKLoginKit") { version = "16.1.3" }

        noPodspec()
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
                implementation("androidx.activity:activity-ktx:1.7.2")
                implementation("androidx.appcompat:appcompat:1.6.1")
                implementation("com.google.firebase:firebase-auth-ktx")
                implementation("com.google.android.gms:play-services-auth:20.7.0")
                implementation("com.facebook.android:facebook-login:16.1.3")
            }
        }
    }
}

android {
    namespace = "com.nekzabirov.firebaseauth"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
    }
}
