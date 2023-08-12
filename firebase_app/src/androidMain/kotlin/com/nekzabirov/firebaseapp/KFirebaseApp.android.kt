package com.nekzabirov.firebaseapp

import com.google.firebase.FirebaseApp

actual typealias KFirebaseApp = FirebaseApp

actual fun initializeFirebase(context: KContext): KFirebaseApp {
    return FirebaseApp.initializeApp(context)!!
}