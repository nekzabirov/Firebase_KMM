package com.nekzabirov.firebaseauth.credential

interface AuthCredential {
    val provider: String

    val signInMethod: String
}