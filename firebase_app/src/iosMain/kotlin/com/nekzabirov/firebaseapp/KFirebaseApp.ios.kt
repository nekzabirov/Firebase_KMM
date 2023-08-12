package com.nekzabirov.firebaseapp

import cocoapods.FirebaseCore.FIRApp

actual typealias KFirebaseApp = FIRApp

actual fun KFirebaseApp.initialize(context: KContext) {
    FIRApp.initialize()
}