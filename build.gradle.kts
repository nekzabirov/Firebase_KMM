plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.library").version("8.1.0-rc01").apply(false)
    kotlin("multiplatform").version("1.9.0").apply(false)
    id("com.google.gms.google-services").version("4.3.15").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
    subprojects {
        delete(buildDir)
    }
}
