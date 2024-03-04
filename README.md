<h1>Firebase SDK for Kotlin Multiplatform KMM <img alt="version" src="https://img.shields.io/badge/version-1.0.4-yellow.svg"> </h1>

The firebase SDK that you can implement into your common dependency of KMM project with supporting target <strong>iOS</strong> & <strong>Android</strong>.

## Supporting targets

| Target  | Min version |
|---------|:------------|
| Android | 26          |
| Ios     | 14.1        |


## Available libraries

The following libraries are available for the various Firebase products.

| Service or Product	                                                         | Gradle Dependency                                            | Nek |
|--------------------------------------------------------------------------------|:-------------------------------------------------------------| ---- |
| [Authentication](https://firebase.google.com/docs/auth)                        | `com.nekzabirov.firebase:firebase_auth:$last_version`        |
| [Cloud Firestore](https://firebase.google.com/docs/firestore)                  | `com.nekzabirov.firebase:firebase_firestore:$last_version`   |
| [Cloud Functions](https://firebase.google.com/docs/functions)                  | `com.nekzabirov.firebase:firebase_functions:$last_version`   |
| [Cloud Storage](https://firebase.google.com/docs/storage)                      | `com.nekzabirov.firebase:firebase_storage:$last_version`     |

Is the Firebase library or API you need missing? [Create an issue](https://github.com/nekzabirov/Firebase_KMM/issues/new?labels=API+coverage&template=increase-api-coverage.md&title=Add+%5Bclass+name%5D.%5Bfunction+name%5D+to+%5Blibrary+name%5D+for+%5Bplatform+names%5D) to request additional API coverage or be awesome and [submit a PR](https://github.com/nekzabirov/Firebase_KMM/fork)

## Setup

<h3>Add cocapods plugin</h3>

```
plugins {
    ...
    kotlin("native.cocoapods")
    ...
}
```

```
cocoapods {
    ...
    pod("FirebaseCore") {
        extraOpts += listOf("-compiler-option", "-fmodules")
    }
    ...
}
```

`ios.deploymentTarget` is important must be 17.2 or newer

<h3>Dependencies</h3>

<h4>Common</h4>

```
val commonMain by getting {
    dependencies {
        ...
        api("com.nekzabirov.firebase:firebase_app:$last_version")
        ...
    }
}
```


