package com.nekzabirov.firebaseauth.provider

import com.google.firebase.auth.GoogleAuthProvider
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl

internal actual fun kGetCredential(idToken: String?, accessToken: String?): AuthCredential =
    AuthCredentialImpl(GoogleAuthProvider.getCredential(idToken, accessToken))