package com.nekzabirov.firebaseapp

import cocoapods.FirebaseCore.FIRApp

actual typealias KFirebaseApp = FIRApp

actual fun initializeFirebase(context: KContext): KFirebaseApp {
    FIRApp.configure()
    FIRApp.initialize()
    return FIRApp.defaultApp()!!
}