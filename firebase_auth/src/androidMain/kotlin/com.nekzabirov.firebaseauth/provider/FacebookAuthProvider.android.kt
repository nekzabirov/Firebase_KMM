package com.nekzabirov.firebaseauth.provider

import com.google.firebase.auth.FacebookAuthProvider
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl

internal actual fun kGetCredential(accessToken: String): AuthCredential =
    AuthCredentialImpl(FacebookAuthProvider.getCredential(accessToken))