package com.nekzabirov.firebaseapp

import com.google.firebase.FirebaseApp

actual typealias KFirebaseApp = FirebaseApp

actual fun KFirebaseApp.initialize(context: KContext) {
    FirebaseApp.initializeApp(context)
}