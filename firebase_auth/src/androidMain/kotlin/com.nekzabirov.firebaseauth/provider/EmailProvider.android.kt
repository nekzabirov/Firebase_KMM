package com.nekzabirov.firebaseauth.provider

import com.google.firebase.auth.EmailAuthProvider
import com.nekzabirov.firebaseauth.credential.AuthCredential
import com.nekzabirov.firebaseauth.credential.AuthCredentialImpl

internal actual fun kGetCredential(email: String, password: String): AuthCredential =
    AuthCredentialImpl(EmailAuthProvider.getCredential(email, password))

internal actual fun kGetCredentialWithLink(email: String, emailLink: String): AuthCredential =
    AuthCredentialImpl(EmailAuthProvider.getCredentialWithLink(email, emailLink))